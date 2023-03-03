package types

type StartRegistrationParameters struct {
	Name string
	Email string
}

type StartRegistrationOutput struct {
	Error *string
}