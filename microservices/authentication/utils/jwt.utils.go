package utils

import (
	"log"
	"time"

	"github.com/golang-jwt/jwt/v4"

	customErrors "authentication/errors"
)

const jwtSigningSecert= "secret"

type JwtPayload struct {
	jwt.RegisteredClaims

	Email string `json:"email"`
}

func CreateJwt(email string) (string, *string) {

	jwtPayload := JwtPayload{
		Email: email,

		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now( ).Add(time.Hour * 24)),
		},
	}

	token, error := jwt.NewWithClaims(jwt.SigningMethodHS256, &jwtPayload).
		SignedString([]byte(jwtSigningSecert))

	if error != nil {
		log.Println("💀 error signin JWT : ", error.Error( ))

		return token, &customErrors.ServerError
	}

	return token, nil
}

func VerifyJwt(token string) (bool, *string) {

	var (
		jwtPayload JwtPayload
		verifyJwtError string
	)

	_, error := jwt.ParseWithClaims(
		token, &jwtPayload,
			func(t *jwt.Token) (interface{ }, error) {
				return []byte(jwtSigningSecert), nil
			},
	)

	if error != nil {

		if error == jwt.ErrSignatureInvalid {
			verifyJwtError= "jwt expired or not found"
		} else {
			log.Println("💀 error verifying JWT : ", error.Error( ))

			verifyJwtError= customErrors.ServerError
		}

		return false, &verifyJwtError
	}

	return true, nil
}