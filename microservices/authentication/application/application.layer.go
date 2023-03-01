package application

import (
	"net/mail"

	businessLogic "authentication/domain/business-logic"
	customErrors "authentication/errors"
	"authentication/ports"
	"authentication/types"
)

type ApplicationLayer struct {
	BusinessLogicLayer *businessLogic.BusinessLogicLayer
	MessagingLayer ports.MessagingPort
}

func(instance *ApplicationLayer) StartRegistration(request *types.StartRegistrationRequest) *types.StartRegistrationResponse {

	_, error := mail.ParseAddress(request.Email)
	if error!= nil {
		return &types.StartRegistrationResponse{ Error: &customErrors.EmailValidationError }}

	if len(request.Name) < 3 || len(request.Name) > 50 {
		return &types.StartRegistrationResponse{ Error: &customErrors.NameValidationError }}

	response := instance.BusinessLogicLayer.StartRegistration(request)

	instance.MessagingLayer.SendOTP(request.Email)

	return response
}

func(instance *ApplicationLayer) SetTemporaryUserVerified(request *types.SetTemporaryUserVerifiedRequest) *types.SetTemporaryUserVerifiedResponse {
	return instance.BusinessLogicLayer.SetTemporaryUserVerified(request)}

func(instance *ApplicationLayer) Register(request *types.RegisterRequest) *types.RegisterResponse {
	name, response := instance.BusinessLogicLayer.Register(request)

	instance.MessagingLayer.CreateProfile(*name, request.Email)

	return response
}

func(instance *ApplicationLayer) Signin(request *types.SigninRequest) *types.SigninResponse {
	return instance.BusinessLogicLayer.Signin(request)}