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

	response := grpcService.ApplicationLayer.
		StartRegistration(
			&types.StartRegistrationRequest{
				Name: request.Name,
				Email: request.Email,
			})

	return &protocGenerated.StartRegistrationResponse{ Error: response.Error }, nil
}

func(grpcService *ImplementedAuthenticationGrpcService) Register(
	ctx context.Context, request *protocGenerated.RegisterReqeust) (*protocGenerated.RegisterResponse, error) {

	response := grpcService.ApplicationLayer.
		Register(
			&types.RegisterRequest{
				Email: request.Email,
				Password: request.Password,
			})

	return &protocGenerated.RegisterResponse{ Jwt: response.Jwt, Error: response.Error }, nil
}

func(grpcService *ImplementedAuthenticationGrpcService) Signin(
	ctx context.Context, request *protocGenerated.SigninReqeust) (*protocGenerated.SigninResponse, error) {

	response := grpcService.ApplicationLayer.
		Signin(
			&types.SigninRequest{
				Email: request.Email,
				Password: request.Password,
			})

	return &protocGenerated.SigninResponse{ Jwt: response.Jwt, Error: response.Error }, nil
}