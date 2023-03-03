package outboundAdapters

import (
	"encoding/json"
	"log"
	"time"

	"github.com/go-redis/redis"

	valueObjects "authentication/domain/value-objects"
	customErrors "authentication/errors"
)

type RedisAdapter struct {
	Connection *redis.Client
}

func(instance *RedisAdapter) Connect( ) {

	instance.Connection= redis.NewClient(
		&redis.Options{
			Addr: "localhost:6379",

			Password: "password",
			DB: 0,
		},
	)

	_, error := instance.Connection.Ping( ).Result( )
	if error != nil {
		log.Fatal("💀 error connecting to redis : ", error.Error( )) }
	log.Println("🔥 connected to redis")
}

func(instance *RedisAdapter) Disconnect( ) {
	if instance.Connection != nil {
		instance.Connection.Close( )}
}

func(instance *RedisAdapter) SaveTemporaryUserDetails(temporaryUserDetails *valueObjects.TemporaryUserDetails) *string {

	record, error := json.Marshal(temporaryUserDetails)

	if error != nil {
		log.Println("💀 error marshalling temporary user details : ", error.Error( ))
		return &customErrors.ServerError}

	error= instance.Connection.Set(temporaryUserDetails.Email, record, 600 * time.Second).Err( )
	if error != nil {
		log.Println("💀 error inserting temporary user details in redis : ", error.Error( ))
		return &customErrors.ServerError}

	return nil
}

func(instance *RedisAdapter) GetTemporaryUser(email string) (*valueObjects.TemporaryUserDetails, *string) {

	record, error := instance.Connection.Get(email).Result( )
	if error != nil {
		log.Println("💀 error fetching temporary user details from redis : ", error.Error( ))
		return nil, &customErrors.ServerError}

	var temporaryUserDetails valueObjects.TemporaryUserDetails

	error= json.Unmarshal([ ]byte(record), &temporaryUserDetails)
	if error != nil {
		log.Println("💀 error unmarshalling temporary user details record from redis : ", error.Error( ))
		return nil, &customErrors.ServerError}

	return &temporaryUserDetails, nil
}

func(instance *RedisAdapter) EvictTemporaryUser(email string) {
	error := instance.Connection.Del(email).Err( )
	if error != nil {
		log.Println("💀 error evicting temporary user details from redis : ", error.Error( ))}
}