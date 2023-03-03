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
				Name: request.Name,
				Email: request.Email,
			})

	return &protocGenerated.StartRegistrationResponse{ Error: output.Error }, nil
}

func(grpcService *ImplementedAuthenticationGrpcService) Register(
	ctx context.Context, request *protocGenerated.RegisterReqeust) (*protocGenerated.RegisterResponse, error) {

	output := grpcService.ApplicationLayer.
		Register(
			&types.RegisterParameters{
				Email: request.Email,
				Password: request.Password,
			})

	return &protocGenerated.RegisterResponse{ Jwt: output.Jwt, Error: output.Error }, nil
}

func(grpcService *ImplementedAuthenticationGrpcService) Signin(
	ctx context.Context, request *protocGenerated.SigninReqeust) (*protocGenerated.SigninResponse, error) {

	output := grpcService.ApplicationLayer.
		Signin(
			&types.SigninParameters{
				Email: request.Email,
				Password: request.Password,
			})

	return &protocGenerated.SigninResponse{ Jwt: output.Jwt, Error: output.Error }, nil
}