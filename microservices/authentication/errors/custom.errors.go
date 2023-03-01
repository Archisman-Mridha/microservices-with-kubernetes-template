package customErrors

var (
	ServerError= "server error occured"

	EmailValidationError= "error validating email"
	NameValidationError= "name should be between 3 to 50 characters"
	EmailPreregisteredError= "email is pre-registered"
	UserNotFoundError= "email not registered"
	WrongPasswordError= "wrong password provided"
)