package types

type RegisterRequest struct {
	Email string
	Password string
}

type RegisterResponse struct {
	Jwt string

	Error *string
}