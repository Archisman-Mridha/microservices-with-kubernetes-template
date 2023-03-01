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

func(instance *CockroachDBAdapter) ApplyPreRegisteredEmailFilter(email string) *string {
	_, error := instance.generatedQueryAppliers.FindRegisteredEmail(context.Background( ), email)

	if error == sql.ErrNoRows { return nil }

	if error != nil {
		return &customErrors.ServerError}

	return &customErrors.EmailPreregisteredError
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