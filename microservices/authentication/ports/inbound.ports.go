package ports

import (
	"authentication/types"
)

type ApplicationPort interface {

	StartRegistration(request *types.StartRegistrationParameters) *types.StartRegistrationOutput
	Register(request *types.RegisterParameters) *types.RegisterOutput

	Signin(request *types.SigninParameters) *types.SigninOutput
}