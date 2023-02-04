package posts.generated.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.0)",
    comments = "Source: main.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PostsGrpc {

  private PostsGrpc() {}

  public static final String SERVICE_NAME = "posts.Posts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<posts.generated.proto.CreatePostRequest,
      posts.generated.proto.CreatePostResponse> getCreatePostMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePost",
      requestType = posts.generated.proto.CreatePostRequest.class,
      responseType = posts.generated.proto.CreatePostResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<posts.generated.proto.CreatePostRequest,
      posts.generated.proto.CreatePostResponse> getCreatePostMethod() {
    io.grpc.MethodDescriptor<posts.generated.proto.CreatePostRequest, posts.generated.proto.CreatePostResponse> getCreatePostMethod;
    if ((getCreatePostMethod = PostsGrpc.getCreatePostMethod) == null) {
      synchronized (PostsGrpc.class) {
        if ((getCreatePostMethod = PostsGrpc.getCreatePostMethod) == null) {
          PostsGrpc.getCreatePostMethod = getCreatePostMethod =
              io.grpc.MethodDescriptor.<posts.generated.proto.CreatePostRequest, posts.generated.proto.CreatePostResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePost"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  posts.generated.proto.CreatePostRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  posts.generated.proto.CreatePostResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PostsMethodDescriptorSupplier("CreatePost"))
              .build();
        }
      }
    }
    return getCreatePostMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PostsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PostsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PostsStub>() {
        @java.lang.Override
        public PostsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PostsStub(channel, callOptions);
        }
      };
    return PostsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PostsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PostsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PostsBlockingStub>() {
        @java.lang.Override
        public PostsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PostsBlockingStub(channel, callOptions);
        }
      };
    return PostsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PostsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PostsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PostsFutureStub>() {
        @java.lang.Override
        public PostsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PostsFutureStub(channel, callOptions);
        }
      };
    return PostsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PostsImplBase implements io.grpc.BindableService {

    /**
     */
    public void createPost(posts.generated.proto.CreatePostRequest request,
        io.grpc.stub.StreamObserver<posts.generated.proto.CreatePostResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePostMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreatePostMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                posts.generated.proto.CreatePostRequest,
                posts.generated.proto.CreatePostResponse>(
                  this, METHODID_CREATE_POST)))
          .build();
    }
  }

  /**
   */
  public static final class PostsStub extends io.grpc.stub.AbstractAsyncStub<PostsStub> {
    private PostsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PostsStub(channel, callOptions);
    }

    /**
     */
    public void createPost(posts.generated.proto.CreatePostRequest request,
        io.grpc.stub.StreamObserver<posts.generated.proto.CreatePostResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePostMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PostsBlockingStub extends io.grpc.stub.AbstractBlockingStub<PostsBlockingStub> {
    private PostsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PostsBlockingStub(channel, callOptions);
    }

    /**
     */
    public posts.generated.proto.CreatePostResponse createPost(posts.generated.proto.CreatePostRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePostMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PostsFutureStub extends io.grpc.stub.AbstractFutureStub<PostsFutureStub> {
    private PostsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PostsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<posts.generated.proto.CreatePostResponse> createPost(
        posts.generated.proto.CreatePostRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePostMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_POST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PostsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PostsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_POST:
          serviceImpl.createPost((posts.generated.proto.CreatePostRequest) request,
              (io.grpc.stub.StreamObserver<posts.generated.proto.CreatePostResponse>) responseObserver);
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

  private static abstract class PostsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PostsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return posts.generated.proto.Main.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Posts");
    }
  }

  private static final class PostsFileDescriptorSupplier
      extends PostsBaseDescriptorSupplier {
    PostsFileDescriptorSupplier() {}
  }

  private static final class PostsMethodDescriptorSupplier
      extends PostsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PostsMethodDescriptorSupplier(String methodName) {
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
      synchronized (PostsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PostsFileDescriptorSupplier())
              .addMethod(getCreatePostMethod())
              .build();
        }
      }
    }
    return result;
  }
}
