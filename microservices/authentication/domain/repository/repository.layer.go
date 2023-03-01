package repository

import "authentication/ports"

type RepositoryLayer struct {
	UsersRepository ports.UsersRepositoryPort

	CacheRepository ports.CacheRepositoryPort
}