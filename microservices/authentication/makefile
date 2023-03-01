protoc-generate:
# code generation for domain events
	rm -rf ./domain/events
	mkdir ./domain/events

	protoc --go_out=./domain --go-grpc_out=./domain --experimental_allow_proto3_optional \
		--go-grpc_opt=paths=source_relative --go_opt=paths=source_relative \
		--proto_path=./proto ./proto/events/*.proto

# code generation for gRPC and messages
	rm -rf ./generated/proto
	mkdir ./generated/proto

	protoc --go_out=./generated/proto --go-grpc_out=./generated/proto --experimental_allow_proto3_optional \
		--go-grpc_opt=paths=source_relative --go_opt=paths=source_relative \
		--proto_path=./proto ./proto/grpc/*.proto ./proto/messages/*.proto