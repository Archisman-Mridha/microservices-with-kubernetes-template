// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.16.0

package sqlc

import (
	"context"
)

type Querier interface {
	CreateUser(ctx context.Context, arg CreateUserParams) error
	FindRegisteredEmail(ctx context.Context, email string) (string, error)
	GetPasswordForEmail(ctx context.Context, email string) (string, error)
}

var _ Querier = (*Queries)(nil)
