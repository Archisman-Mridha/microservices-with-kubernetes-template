// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: verify-otp.proto

package com.otp.generated.proto;

public interface VerifyOtpRequestOrBuilder
        extends
        // @@protoc_insertion_point(interface_extends:otp.VerifyOtpRequest)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string email = 1;</code>
     *
     * @return The email.
     */
    java.lang.String getEmail();
    /**
     * <code>string email = 1;</code>
     *
     * @return The bytes for email.
     */
    com.google.protobuf.ByteString getEmailBytes();

    /**
     * <code>string otp = 2;</code>
     *
     * @return The otp.
     */
    java.lang.String getOtp();
    /**
     * <code>string otp = 2;</code>
     *
     * @return The bytes for otp.
     */
    com.google.protobuf.ByteString getOtpBytes();
}