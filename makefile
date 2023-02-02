compose-up:
	docker-compose up -d

compose-down:
	docker-compose down

generate-migration-files:
	migrate create -ext sql -dir $(path) -seq $(sequence)

apply-migrations:
	chmod +x ./scripts/run-cockroachdb-migrations.sh
	bash ./scripts/run-cockroachdb-migrations.sh $(operation) $(path)