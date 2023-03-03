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

func(instance *ApplicationLayer) StartRegistration(parameters *types.StartRegistrationParameters) *types.StartRegistrationOutput {

	_, error := mail.ParseAddress(parameters.Email)
	if error!= nil {
		return &types.StartRegistrationOutput{ Error: &customErrors.EmailValidationError }}

	if len(parameters.Name) < 3 || len(parameters.Name) > 50 {
		return &types.StartRegistrationOutput{ Error: &customErrors.NameValidationError }}

	output := instance.BusinessLogicLayer.StartRegistration(parameters)

	instance.MessagingLayer.SendOTP(parameters.Email)

	return output
}

func(instance *ApplicationLayer) SetTemporaryUserVerified(parameters *types.SetTemporaryUserVerifiedParameters) *types.SetTemporaryUserVerifiedOutput {
	return instance.BusinessLogicLayer.SetTemporaryUserVerified(parameters)}

func(instance *ApplicationLayer) Register(parameters *types.RegisterParameters) *types.RegisterOutput {
	name, output := instance.BusinessLogicLayer.Register(parameters)

	instance.MessagingLayer.CreateProfile(*name, parameters.Email)

	return output
}

func(instance *ApplicationLayer) Signin(parameters *types.SigninParameters) *types.SigninOutput {
	return instance.BusinessLogicLayer.Signin(parameters)}