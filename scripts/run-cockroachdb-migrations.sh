migration_operation=$1
migration_path=$2

migrate \
    -database cockroach://root@localhost:26257/defaultdb?sslmode=disable \
    -path $migration_path $migration_operation