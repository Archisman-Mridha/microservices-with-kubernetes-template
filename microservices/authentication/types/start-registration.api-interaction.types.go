package types

type StartRegistrationRequest struct {
	Name string
	Email string
}

type StartRegistrationResponse struct {
	Error *string
}