package grpcServices

import (
	"context"

	protocGenerated "authentication/generated/proto/grpc"
	"authentication/ports"
	"authentication/types"
)

type ImplementedAuthenticationGrpcService struct {
	*protocGenerated.UnimplementedAuthenticationServer

	ApplicationLayer ports.ApplicationPort
}

func(grpcService *ImplementedAuthenticationGrpcService) StartRegistration(
	ctx context.Context, request *protocGenerated.StartRegistrationRequest) (*protocGenerated.StartRegistrationResponse, error) {

	output := grpcService.ApplicationLayer.
		StartRegistration(
			&types.StartRegistrationParameters{
				Email: request.Email,
				Username: request.Username,
				Password: request.Password,
			})

	return &protocGenerated.StartRegistrationResponse{ Errors: output.Errors }, nil
}

func(grpcService *ImplementedAuthenticationGrpcService) Signin(
	ctx context.Context, request *protocGenerated.SigninRequest) (*protocGenerated.SigninResponse, error) {

	output := grpcService.ApplicationLayer.
		Signin(
			&types.SigninParameters{
				Email: request.Email,
				Password: request.Password,
			})

	return &protocGenerated.SigninResponse{ Jwt: output.Jwt, Error: output.Error }, nil
}