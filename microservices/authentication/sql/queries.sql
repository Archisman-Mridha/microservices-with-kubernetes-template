-- name: FindDuplicateUser :many
SELECT * FROM users
    WHERE users.email= @email OR users.username= @username
        LIMIT 2;

-- name: CreateUser :exec
INSERT INTO users
    (username, email, password)
        VALUES (@username, @email, @password);

-- name: GetPasswordForEmail :one
SELECT password FROM users
    WHERE users.email= @email
        LIMIT 1;