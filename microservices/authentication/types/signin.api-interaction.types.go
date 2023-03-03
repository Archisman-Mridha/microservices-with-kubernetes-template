package types

type SigninParameters struct {
	Email string
	Password string
}

type SigninOutput struct {
	Jwt string

	Error *string
}