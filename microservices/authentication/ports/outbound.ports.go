package ports

import (
	"authentication/domain/entities"
	valueObjects "authentication/domain/value-objects"
	"authentication/types"
)

type UsersRepositoryPort interface {
	ApplyPreregisteredUserFilter(email string, username string) []string
	CreateUser(userEntity entities.UserEntity) *string

	GetPasswordForEmail(email string) (*string, *string)
}

type CacheRepositoryPort interface {
	SaveTemporaryUserDetails(*valueObjects.TemporaryUserDetails) *string
	GetTemporaryUser(email string) (*valueObjects.TemporaryUserDetails, *string)
	EvictTemporaryUser(email string)
}

type MessagingPort interface {
	SendOTP(email string)
	CreateProfile(profileDetails *types.ProfileDetails)
}