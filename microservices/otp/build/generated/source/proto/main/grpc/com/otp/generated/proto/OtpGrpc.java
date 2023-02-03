package com.otp.generated.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.52.0)",
        comments = "Source: main.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OtpGrpc {

    private OtpGrpc() {}

    public static final String SERVICE_NAME = "otp.Otp";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<
                    com.otp.generated.proto.VerifyOtpRequest,
                    com.otp.generated.proto.VerifyOtpResponse>
            getVerifyOtpMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "VerifyOtp",
            requestType = com.otp.generated.proto.VerifyOtpRequest.class,
            responseType = com.otp.generated.proto.VerifyOtpResponse.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<
                    com.otp.generated.proto.VerifyOtpRequest,
                    com.otp.generated.proto.VerifyOtpResponse>
            getVerifyOtpMethod() {
        io.grpc.MethodDescriptor<
                        com.otp.generated.proto.VerifyOtpRequest,
                        com.otp.generated.proto.VerifyOtpResponse>
                getVerifyOtpMethod;
        if ((getVerifyOtpMethod = OtpGrpc.getVerifyOtpMethod) == null) {
            synchronized (OtpGrpc.class) {
                if ((getVerifyOtpMethod = OtpGrpc.getVerifyOtpMethod) == null) {
                    OtpGrpc.getVerifyOtpMethod =
                            getVerifyOtpMethod =
                                    io.grpc.MethodDescriptor
                                            .<com.otp.generated.proto.VerifyOtpRequest,
                                                    com.otp.generated.proto.VerifyOtpResponse>
                                                    newBuilder()
                                            .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                            .setFullMethodName(
                                                    generateFullMethodName(
                                                            SERVICE_NAME, "VerifyOtp"))
                                            .setSampledToLocalTracing(true)
                                            .setRequestMarshaller(
                                                    io.grpc.protobuf.ProtoUtils.marshaller(
                                                            com.otp.generated.proto.VerifyOtpRequest
                                                                    .getDefaultInstance()))
                                            .setResponseMarshaller(
                                                    io.grpc.protobuf.ProtoUtils.marshaller(
                                                            com.otp.generated.proto
                                                                    .VerifyOtpResponse
                                                                    .getDefaultInstance()))
                                            .setSchemaDescriptor(
                                                    new OtpMethodDescriptorSupplier("VerifyOtp"))
                                            .build();
                }
            }
        }
        return getVerifyOtpMethod;
    }

    /** Creates a new async stub that supports all call types for the service */
    public static OtpStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<OtpStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<OtpStub>() {
                    @java.lang.Override
                    public OtpStub newStub(
                            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new OtpStub(channel, callOptions);
                    }
                };
        return OtpStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the
     * service
     */
    public static OtpBlockingStub newBlockingStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<OtpBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<OtpBlockingStub>() {
                    @java.lang.Override
                    public OtpBlockingStub newStub(
                            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new OtpBlockingStub(channel, callOptions);
                    }
                };
        return OtpBlockingStub.newStub(factory, channel);
    }

    /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
    public static OtpFutureStub newFutureStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<OtpFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<OtpFutureStub>() {
                    @java.lang.Override
                    public OtpFutureStub newStub(
                            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new OtpFutureStub(channel, callOptions);
                    }
                };
        return OtpFutureStub.newStub(factory, channel);
    }

    /** */
    public abstract static class OtpImplBase implements io.grpc.BindableService {

        /** */
        public void verifyOtp(
                com.otp.generated.proto.VerifyOtpRequest request,
                io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtpResponse>
                        responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
                    getVerifyOtpMethod(), responseObserver);
        }

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getVerifyOtpMethod(),
                            io.grpc.stub.ServerCalls.asyncUnaryCall(
                                    new MethodHandlers<
                                            com.otp.generated.proto.VerifyOtpRequest,
                                            com.otp.generated.proto.VerifyOtpResponse>(
                                            this, METHODID_VERIFY_OTP)))
                    .build();
        }
    }

    /** */
    public static final class OtpStub extends io.grpc.stub.AbstractAsyncStub<OtpStub> {
        private OtpStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected OtpStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new OtpStub(channel, callOptions);
        }

        /** */
        public void verifyOtp(
                com.otp.generated.proto.VerifyOtpRequest request,
                io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtpResponse>
                        responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getVerifyOtpMethod(), getCallOptions()),
                    request,
                    responseObserver);
        }
    }

    /** */
    public static final class OtpBlockingStub
            extends io.grpc.stub.AbstractBlockingStub<OtpBlockingStub> {
        private OtpBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected OtpBlockingStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new OtpBlockingStub(channel, callOptions);
        }

        /** */
        public com.otp.generated.proto.VerifyOtpResponse verifyOtp(
                com.otp.generated.proto.VerifyOtpRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(
                    getChannel(), getVerifyOtpMethod(), getCallOptions(), request);
        }
    }

    /** */
    public static final class OtpFutureStub extends io.grpc.stub.AbstractFutureStub<OtpFutureStub> {
        private OtpFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected OtpFutureStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new OtpFutureStub(channel, callOptions);
        }

        /** */
        public com.google.common.util.concurrent.ListenableFuture<
                        com.otp.generated.proto.VerifyOtpResponse>
                verifyOtp(com.otp.generated.proto.VerifyOtpRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(
                    getChannel().newCall(getVerifyOtpMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_VERIFY_OTP = 0;

    private static final class MethodHandlers<Req, Resp>
            implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
                    io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
                    io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
                    io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final OtpImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(OtpImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_VERIFY_OTP:
                    serviceImpl.verifyOtp(
                            (com.otp.generated.proto.VerifyOtpRequest) request,
                            (io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtpResponse>)
                                    responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private abstract static class OtpBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
                    io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        OtpBaseDescriptorSupplier() {}

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return com.otp.generated.proto.Main.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("Otp");
        }
    }

    private static final class OtpFileDescriptorSupplier extends OtpBaseDescriptorSupplier {
        OtpFileDescriptorSupplier() {}
    }

    private static final class OtpMethodDescriptorSupplier extends OtpBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        OtpMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (OtpGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor =
                            result =
                                    io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                                            .setSchemaDescriptor(new OtpFileDescriptorSupplier())
                                            .addMethod(getVerifyOtpMethod())
                                            .build();
                }
            }
        }
        return result;
    }
}
