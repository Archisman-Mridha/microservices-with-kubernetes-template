package ports

import (
	"authentication/types"
)

type ApplicationPort interface {

	StartRegistration(request *types.StartRegistrationRequest) *types.StartRegistrationResponse
	SetTemporaryUserVerified(request *types.SetTemporaryUserVerifiedRequest) *types.SetTemporaryUserVerifiedResponse
	Register(request *types.RegisterRequest) *types.RegisterResponse

	Signin(request *types.SigninRequest) *types.SigninResponse
}