#!/bin/bash

mvn clean install

mv ./src/main/java/com/otp/generated/proto/com/otp/generated/proto/OTPGrpc.java \
    ./src/main/java/com/otp/generated/proto/OTPGrpc.java

rm -rf ./src/main/java/com/otp/generated/proto/com

for file in $(ls ./src/main/proto); do
    if [ "$file" != "main.proto" ]; then
        protoc --java_out=./src/main/java -I=./src/main/proto $file
    fi
done

java \
    -jar javafmt.jar \
    --aosp -r -i 4 \
        ./src/main/java/com/otp/generated/proto/*.java