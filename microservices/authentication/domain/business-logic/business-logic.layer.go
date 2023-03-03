package businessLogic

import (
	"authentication/domain/entities"
	"authentication/domain/repository"
	valueObjects "authentication/domain/value-objects"
	customErrors "authentication/errors"
	"authentication/types"
	"authentication/utils"
)

type BusinessLogicLayer struct {
	RepositoryLayer *repository.RepositoryLayer
}

func(instance *BusinessLogicLayer) StartRegistration(parameters *types.StartRegistrationParameters) *types.StartRegistrationOutput {

	errors := instance.RepositoryLayer.UsersRepository.ApplyPreregisteredUserFilter(parameters.Email, parameters.Username)
	if len(errors) != 0 {
		return &types.StartRegistrationOutput{ Errors: errors }}

	error := instance.RepositoryLayer.CacheRepository.SaveTemporaryUserDetails(
		&valueObjects.TemporaryUserDetails{
			Email: parameters.Email,
			Username: parameters.Username,
			Password: parameters.Password,
		},
	)
	if error != nil {
		errors= append(errors, *error)
		return &types.StartRegistrationOutput{ Errors: errors }}

	return &types.StartRegistrationOutput{ }
}

func(instance *BusinessLogicLayer) Register(parameters *types.RegisterParameters) *types.RegisterBusinessLayerOutput {
	var (
		temporaryUserDetails *valueObjects.TemporaryUserDetails
		error *string
	)

	if temporaryUserDetails, error= instance.RepositoryLayer.CacheRepository.GetTemporaryUser(parameters.Email);
		error != nil {
			return &types.RegisterBusinessLayerOutput{ Error: error }}

	if error := instance.RepositoryLayer.UsersRepository.CreateUser(
		entities.UserEntity{
			Email: parameters.Email,
			Username: temporaryUserDetails.Username,
			Password: temporaryUserDetails.Password,
		},
	);
		error != nil {
			return &types.RegisterBusinessLayerOutput{ Error: error }}

	// NOTE: didn't evict the temporary user details

	return &types.RegisterBusinessLayerOutput{
			ProfileDetails: &types.ProfileDetails{
				Username: temporaryUserDetails.Username,
				Email: temporaryUserDetails.Email,
			},
		}
}

func(instance *BusinessLogicLayer) Signin(parameters *types.SigninParameters) *types.SigninOutput {

	password, error := instance.RepositoryLayer.UsersRepository.GetPasswordForEmail(parameters.Email)
	
	if error != nil {
		return &types.SigninOutput{ Error: error }}

	if *password != parameters.Password {
		return &types.SigninOutput{ Error: &customErrors.WrongPasswordError }}

	jwt, error := utils.CreateJwt(parameters.Email)
	if error != nil {
		return &types.SigninOutput{ Error: error }}

	return &types.SigninOutput{ Jwt: jwt }
}