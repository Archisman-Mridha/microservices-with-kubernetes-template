protoc-generate:
	rm -rf ./generated/proto
	mkdir ./generated/proto

	protoc --go_out=./generated/proto --go-grpc_out=./generated/proto --experimental_allow_proto3_optional \
		--go-grpc_opt=paths=source_relative --go_opt=paths=source_relative \
		--proto_path=./proto ./proto/*.proto