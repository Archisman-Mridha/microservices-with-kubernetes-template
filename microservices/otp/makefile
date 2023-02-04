protoc-generate:
	gradle build
	java -jar javafmt.jar -aosp -r ./build/generated/source/proto/main/grpc/com/otp/generated/proto/*.java && \
		java -jar javafmt.jar -aosp -r ./build/generated/source/proto/main/java/com/otp/generated/proto/*.java

serve:
	gradle build && \
		java -jar ./build/libs/otp-0.0.1-SNAPSHOT.jar