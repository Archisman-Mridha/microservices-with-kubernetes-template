// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main.proto

package com.otp.generated.proto;

public final class Main {
    private Main() {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

    static {
        java.lang.String[] descriptorData = {
            "\n\nmain.proto\022\003otp\032\020verify-otp.proto2C\n\003O"
                    + "tp\022<\n\tVerifyOtp\022\025.otp.VerifyOtpRequest\032\026"
                    + ".otp.VerifyOtpResponse\"\000B\033\n\027com.otp.gene"
                    + "rated.protoP\001b\006proto3"
        };
        descriptor =
                com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
                        descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[] {
                            com.otp.generated.proto.VerifyOtp.getDescriptor(),
                        });
        com.otp.generated.proto.VerifyOtp.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
