package types

type StartRegistrationParameters struct {

	Email string
	Username string
	Password string
}

type StartRegistrationOutput struct {
	Errors []string
}