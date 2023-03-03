package customErrors

var (
	ServerError= "server error occured"

	EmailValidationError= "error validating email"
	UsernameValidationError= "username should be between 3 to 50 characters"
	PasswordValidationError= "password should be between 3 to 50 characters"
	EmailPreregisteredError= "email is pre-registered"
	UsernamePreregisteredError= "username is pre-registered"
	UserNotFoundError= "email not registered"
	WrongPasswordError= "wrong password provided"
)