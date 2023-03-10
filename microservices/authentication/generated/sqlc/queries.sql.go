// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.16.0
// source: queries.sql

package sqlc

import (
	"context"
)

const createUser = `-- name: CreateUser :exec
INSERT INTO users
    (username, email, password)
        VALUES ($1, $2, $3)
`

type CreateUserParams struct {
	Username string
	Email    string
	Password string
}

func (q *Queries) CreateUser(ctx context.Context, arg CreateUserParams) error {
	_, err := q.db.ExecContext(ctx, createUser, arg.Username, arg.Email, arg.Password)
	return err
}

const findDuplicateUser = `-- name: FindDuplicateUser :many
SELECT id, email, username, password FROM users
    WHERE users.email= $1 OR users.username= $2
        LIMIT 2
`

type FindDuplicateUserParams struct {
	Email    string
	Username string
}

func (q *Queries) FindDuplicateUser(ctx context.Context, arg FindDuplicateUserParams) ([]User, error) {
	rows, err := q.db.QueryContext(ctx, findDuplicateUser, arg.Email, arg.Username)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	var items []User
	for rows.Next() {
		var i User
		if err := rows.Scan(
			&i.ID,
			&i.Email,
			&i.Username,
			&i.Password,
		); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	if err := rows.Close(); err != nil {
		return nil, err
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return items, nil
}

const getPasswordForEmail = `-- name: GetPasswordForEmail :one
SELECT password FROM users
    WHERE users.email= $1
        LIMIT 1
`

func (q *Queries) GetPasswordForEmail(ctx context.Context, email string) (string, error) {
	row := q.db.QueryRowContext(ctx, getPasswordForEmail, email)
	var password string
	err := row.Scan(&password)
	return password, err
}
