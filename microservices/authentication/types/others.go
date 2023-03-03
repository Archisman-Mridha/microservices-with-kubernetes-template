package types

type ProfileDetails struct {
	Username string
	Email string
}

type RegisterBusinessLayerOutput struct {
	ProfileDetails *ProfileDetails

	Error *string
}