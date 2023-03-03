package outboundAdapters

import (
	"context"
	"database/sql"
	"log"

	"authentication/domain/entities"
	customErrors "authentication/errors"
	sqlcGenerated "authentication/generated/sqlc"
)

type CockroachDBAdapter struct {
	OutboundAdapter

	Connection *sql.DB
	generatedQueryAppliers *sqlcGenerated.Queries
}

func(instance *CockroachDBAdapter) Connect( ) {
	var error error

	instance.Connection, error= sql.Open("postgres", "postgresql://root@localhost:26257/defaultdb?sslmode=disable")
	if error != nil {
		log.Panic("💀 error connecting to cockroachDB : ", error.Error( )) }

	error= instance.Connection.Ping( )
	if error != nil {
		log.Panic("💀 error pinging cockroachDB : ", error.Error( )) }
	log.Println("🔥 connected to cockroachDB")

	instance.generatedQueryAppliers= sqlcGenerated.New(instance.Connection)
}

func(instance *CockroachDBAdapter) Disconnect( ) {
	if instance.Connection != nil {
		instance.Connection.Close( )}
}

func(instance *CockroachDBAdapter) ApplyPreregisteredUserFilter(email string, username string) []string {

	queryResult, error := instance.generatedQueryAppliers.FindDuplicateUser(context.Background( ),
		sqlcGenerated.FindDuplicateUserParams{
			Email: email,
			Username: username,
		})

	if error == sql.ErrNoRows { return []string{ } }

	if error != nil {
		return []string{ customErrors.ServerError }}

	var errors []string

	for _, row := range queryResult {

		if row.Email == email {
			errors= append(errors, customErrors.EmailPreregisteredError)}

		if row.Username == username {
			errors= append(errors, customErrors.UsernamePreregisteredError)}
	}

	return errors
}

func(instance *CockroachDBAdapter) CreateUser(userEntity entities.UserEntity) *string {

	error := instance.generatedQueryAppliers.CreateUser(context.Background( ), sqlcGenerated.CreateUserParams(userEntity))
	if error != nil {
		log.Println("💀 error creating new user in database : ", error.Error( ))
		return &customErrors.ServerError}

	return nil
}

func(instance *CockroachDBAdapter) GetPasswordForEmail(email string) (*string, *string) {
	password, error :=instance.generatedQueryAppliers.GetPasswordForEmail(context.Background( ), email)
	if error != nil {
		return nil, &customErrors.ServerError}

	return &password, nil
}