package ports

import (
	"authentication/domain/entities"
	valueObjects "authentication/domain/value-objects"
)

type UsersRepositoryPort interface {
	ApplyPreRegisteredEmailFilter(email string) *string
	CreateUser(userEntity entities.UserEntity) *string

	GetPasswordForEmail(email string) (*string, *string)
}

type CacheRepositoryPort interface {
	SaveTemporaryUserDetails(*valueObjects.TemporaryUserDetails) *string
	GetTemporaryUserDetails(email string) (*valueObjects.TemporaryUserDetails, *string)
	DeleteTemporaryUserDetails(email string) *string

	SetTemporaryUserVerified(email string) error
}

type MessagingPort interface {
	SendOTP(email string)
	CreateProfile(name string, email string)
}