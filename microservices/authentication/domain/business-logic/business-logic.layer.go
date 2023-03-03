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

	error := instance.RepositoryLayer.UsersRepository.ApplyPreRegisteredEmailFilter(parameters.Email)
	if error != nil {
		return &types.StartRegistrationOutput{ Error: &customErrors.ServerError }}

	error= instance.RepositoryLayer.CacheRepository.SaveTemporaryUserDetails(
		&valueObjects.TemporaryUserDetails{
			Email: parameters.Email,
			Name: parameters.Name,
			IsVerified: false,
		},
	)
	if error != nil {
		return &types.StartRegistrationOutput{ Error: error }}

	return &types.StartRegistrationOutput{ }
}

func(instance *BusinessLogicLayer) SetTemporaryUserVerified(parameters *types.SetTemporaryUserVerifiedParameters) *types.SetTemporaryUserVerifiedOutput {

	if error := instance.RepositoryLayer.CacheRepository.SetTemporaryUserVerified(parameters.Email);
		error != nil {
			return &types.SetTemporaryUserVerifiedOutput{ Error: error }}

	return &types.SetTemporaryUserVerifiedOutput{ }
}

func(instance *BusinessLogicLayer) Register(parameters *types.RegisterParameters) (*string, *types.RegisterOutput) {

	temporaryUserDetails, error := instance.RepositoryLayer.CacheRepository.GetTemporaryUserDetails(parameters.Email)
	if error != nil {
		return nil, &types.RegisterOutput{ Error: error }}

	if error= instance.RepositoryLayer.UsersRepository.CreateUser(
		entities.UserEntity{
			Email: parameters.Email,
			Password: parameters.Password,
		},
	);
		error != nil {
			return nil, &types.RegisterOutput{ Error: error }}

	jwt, error := utils.CreateJwt(temporaryUserDetails.Email)
	if error != nil {
		return nil, &types.RegisterOutput{ Error: error }}

	if error= instance.RepositoryLayer.CacheRepository.DeleteTemporaryUserDetails(parameters.Email);
		error != nil {
			return nil, &types.RegisterOutput{ Error: error }}

	return &temporaryUserDetails.Name, &types.RegisterOutput{ Jwt: jwt }
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