package com.otp.generated.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: main.proto")
public final class OTPGrpc {

  private OTPGrpc() {}

  public static final String SERVICE_NAME = "otp.OTP";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.otp.generated.proto.VerifyOtp.VerifyOTPRequest,
      com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> getVerifyOTPMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VerifyOTP",
      requestType = com.otp.generated.proto.VerifyOtp.VerifyOTPRequest.class,
      responseType = com.otp.generated.proto.VerifyOtp.VerifyOTPResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.otp.generated.proto.VerifyOtp.VerifyOTPRequest,
      com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> getVerifyOTPMethod() {
    io.grpc.MethodDescriptor<com.otp.generated.proto.VerifyOtp.VerifyOTPRequest, com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> getVerifyOTPMethod;
    if ((getVerifyOTPMethod = OTPGrpc.getVerifyOTPMethod) == null) {
      synchronized (OTPGrpc.class) {
        if ((getVerifyOTPMethod = OTPGrpc.getVerifyOTPMethod) == null) {
          OTPGrpc.getVerifyOTPMethod = getVerifyOTPMethod =
              io.grpc.MethodDescriptor.<com.otp.generated.proto.VerifyOtp.VerifyOTPRequest, com.otp.generated.proto.VerifyOtp.VerifyOTPResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VerifyOTP"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.otp.generated.proto.VerifyOtp.VerifyOTPRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.otp.generated.proto.VerifyOtp.VerifyOTPResponse.getDefaultInstance()))
              .setSchemaDescriptor(new OTPMethodDescriptorSupplier("VerifyOTP"))
              .build();
        }
      }
    }
    return getVerifyOTPMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OTPStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OTPStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OTPStub>() {
        @java.lang.Override
        public OTPStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OTPStub(channel, callOptions);
        }
      };
    return OTPStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OTPBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OTPBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OTPBlockingStub>() {
        @java.lang.Override
        public OTPBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OTPBlockingStub(channel, callOptions);
        }
      };
    return OTPBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OTPFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OTPFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OTPFutureStub>() {
        @java.lang.Override
        public OTPFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OTPFutureStub(channel, callOptions);
        }
      };
    return OTPFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class OTPImplBase implements io.grpc.BindableService {

    /**
     */
    public void verifyOTP(com.otp.generated.proto.VerifyOtp.VerifyOTPRequest request,
        io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyOTPMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getVerifyOTPMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.otp.generated.proto.VerifyOtp.VerifyOTPRequest,
                com.otp.generated.proto.VerifyOtp.VerifyOTPResponse>(
                  this, METHODID_VERIFY_OTP)))
          .build();
    }
  }

  /**
   */
  public static final class OTPStub extends io.grpc.stub.AbstractAsyncStub<OTPStub> {
    private OTPStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OTPStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OTPStub(channel, callOptions);
    }

    /**
     */
    public void verifyOTP(com.otp.generated.proto.VerifyOtp.VerifyOTPRequest request,
        io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyOTPMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OTPBlockingStub extends io.grpc.stub.AbstractBlockingStub<OTPBlockingStub> {
    private OTPBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OTPBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OTPBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.otp.generated.proto.VerifyOtp.VerifyOTPResponse verifyOTP(com.otp.generated.proto.VerifyOtp.VerifyOTPRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyOTPMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OTPFutureStub extends io.grpc.stub.AbstractFutureStub<OTPFutureStub> {
    private OTPFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OTPFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OTPFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.otp.generated.proto.VerifyOtp.VerifyOTPResponse> verifyOTP(
        com.otp.generated.proto.VerifyOtp.VerifyOTPRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyOTPMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VERIFY_OTP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OTPImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OTPImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VERIFY_OTP:
          serviceImpl.verifyOTP((com.otp.generated.proto.VerifyOtp.VerifyOTPRequest) request,
              (io.grpc.stub.StreamObserver<com.otp.generated.proto.VerifyOtp.VerifyOTPResponse>) responseObserver);
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

  private static abstract class OTPBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OTPBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.otp.generated.proto.Main.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("OTP");
    }
  }

  private static final class OTPFileDescriptorSupplier
      extends OTPBaseDescriptorSupplier {
    OTPFileDescriptorSupplier() {}
  }

  private static final class OTPMethodDescriptorSupplier
      extends OTPBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OTPMethodDescriptorSupplier(String methodName) {
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
      synchronized (OTPGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OTPFileDescriptorSupplier())
              .addMethod(getVerifyOTPMethod())
              .build();
        }
      }
    }
    return result;
  }
}
