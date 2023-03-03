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

	// -- parameter validations
	_, error := mail.ParseAddress(parameters.Email)
	if error!= nil {
		return &types.StartRegistrationOutput{ Errors: []string{ customErrors.EmailValidationError }}}

	if len(parameters.Username) < 3 || len(parameters.Username) > 50 {
		return &types.StartRegistrationOutput{ Errors: []string{ customErrors.UsernameValidationError }}}

	if len(parameters.Password) < 3 || len(parameters.Password) > 50 {
			return &types.StartRegistrationOutput{ Errors: []string{ customErrors.PasswordValidationError }}}
	// --

	output := instance.BusinessLogicLayer.StartRegistration(parameters)

	instance.MessagingLayer.SendOTP(parameters.Email)

	return output
}

func(instance *ApplicationLayer) Register(parameters *types.RegisterParameters) *types.RegisterOutput {
	output := instance.BusinessLogicLayer.Register(parameters)

	if output.Error == nil {
		instance.MessagingLayer.CreateProfile(output.ProfileDetails)}

	return &types.RegisterOutput{ Error: output.Error }
}

func(instance *ApplicationLayer) Signin(parameters *types.SigninParameters) *types.SigninOutput {
	return instance.BusinessLogicLayer.Signin(parameters)}