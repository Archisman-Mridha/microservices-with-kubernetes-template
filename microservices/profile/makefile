print-schema:
	diesel \
		--database-url "postgresql://root@localhost:26257/profile?sslmode=disable" print-schema \
		> ./src/generated/diesel/schema.rs

	diesel_ext > ./src/generated/diesel/models.rs