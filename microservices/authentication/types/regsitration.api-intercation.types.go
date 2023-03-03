package types

type RegisterParameters struct {
	Email string
	Password string
}

type RegisterOutput struct {
	Jwt string

	Error *string
}