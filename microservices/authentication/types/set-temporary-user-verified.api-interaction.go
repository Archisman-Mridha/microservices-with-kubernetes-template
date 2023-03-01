package types

type SetTemporaryUserVerifiedRequest struct {
	Email string
}

type SetTemporaryUserVerifiedResponse struct {
	Error error
}