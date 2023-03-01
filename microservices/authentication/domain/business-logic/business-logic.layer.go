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

func(instance *BusinessLogicLayer) StartRegistration(request *types.StartRegistrationRequest) *types.StartRegistrationResponse {

	error := instance.RepositoryLayer.UsersRepository.ApplyPreRegisteredEmailFilter(request.Email)
	if error != nil {
		return &types.StartRegistrationResponse{ Error: &customErrors.ServerError }}

	error= instance.RepositoryLayer.CacheRepository.SaveTemporaryUserDetails(
		&valueObjects.TemporaryUserDetails{
			Email: request.Email,
			Name: request.Name,
			IsVerified: false,
		},
	)
	if error != nil {
		return &types.StartRegistrationResponse{ Error: error }}

	return &types.StartRegistrationResponse{ }
}

func(instance *BusinessLogicLayer) SetTemporaryUserVerified(request *types.SetTemporaryUserVerifiedRequest) *types.SetTemporaryUserVerifiedResponse {

	if error := instance.RepositoryLayer.CacheRepository.SetTemporaryUserVerified(request.Email);
		error != nil {
			return &types.SetTemporaryUserVerifiedResponse{ Error: error }}

	return &types.SetTemporaryUserVerifiedResponse{ }
}

func(instance *BusinessLogicLayer) Register(request *types.RegisterRequest) (*string, *types.RegisterResponse) {

	temporaryUserDetails, error := instance.RepositoryLayer.CacheRepository.GetTemporaryUserDetails(request.Email)
	if error != nil {
		return nil, &types.RegisterResponse{ Error: error }}

	if error= instance.RepositoryLayer.UsersRepository.CreateUser(
		entities.UserEntity{
			Email: request.Email,
			Password: request.Password,
		},
	);
		error != nil {
			return nil, &types.RegisterResponse{ Error: error }}

	jwt, error := utils.CreateJwt(temporaryUserDetails.Email)
	if error != nil {
		return nil, &types.RegisterResponse{ Error: error }}

	if error= instance.RepositoryLayer.CacheRepository.DeleteTemporaryUserDetails(request.Email);
		error != nil {
			return nil, &types.RegisterResponse{ Error: error }}

	return &temporaryUserDetails.Name, &types.RegisterResponse{ Jwt: jwt }
}

func(instance *BusinessLogicLayer) Signin(request *types.SigninRequest) *types.SigninResponse {

	password, error := instance.RepositoryLayer.UsersRepository.GetPasswordForEmail(request.Email)
	
	if error != nil {
		return &types.SigninResponse{ Error: error }}

	if *password != request.Password {
		return &types.SigninResponse{ Error: &customErrors.WrongPasswordError }}

	jwt, error := utils.CreateJwt(request.Email)
	if error != nil {
		return &types.SigninResponse{ Error: error }}

	return &types.SigninResponse{ Jwt: jwt }
}