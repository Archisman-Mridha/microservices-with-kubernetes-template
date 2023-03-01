package types

type SigninRequest struct {
	Email string
	Password string
}

type SigninResponse struct {
	Jwt string

	Error *string
}