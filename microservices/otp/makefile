rebuild:
	gradle build
	java -jar javafmt.jar -aosp -r ./build/generated/source/proto/main/grpc/com/otp/generated/proto/*.java && \
		java -jar javafmt.jar -aosp -r ./build/generated/source/proto/main/java/com/otp/generated/proto/*.java

serve:
	cd ../../ && ./gradlew :microservices:otp:run