package com.wordy.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: wordy.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LeaderboardServiceGrpc {

  private LeaderboardServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "wordy.LeaderboardService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.Empty,
      com.wordy.proto.LeaderboardResponse> getGetLeaderboardMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLeaderboard",
      requestType = com.wordy.proto.Empty.class,
      responseType = com.wordy.proto.LeaderboardResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.Empty,
      com.wordy.proto.LeaderboardResponse> getGetLeaderboardMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.Empty, com.wordy.proto.LeaderboardResponse> getGetLeaderboardMethod;
    if ((getGetLeaderboardMethod = LeaderboardServiceGrpc.getGetLeaderboardMethod) == null) {
      synchronized (LeaderboardServiceGrpc.class) {
        if ((getGetLeaderboardMethod = LeaderboardServiceGrpc.getGetLeaderboardMethod) == null) {
          LeaderboardServiceGrpc.getGetLeaderboardMethod = getGetLeaderboardMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.Empty, com.wordy.proto.LeaderboardResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLeaderboard"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.LeaderboardResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LeaderboardServiceMethodDescriptorSupplier("GetLeaderboard"))
              .build();
        }
      }
    }
    return getGetLeaderboardMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LeaderboardServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceStub>() {
        @java.lang.Override
        public LeaderboardServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeaderboardServiceStub(channel, callOptions);
        }
      };
    return LeaderboardServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LeaderboardServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceBlockingStub>() {
        @java.lang.Override
        public LeaderboardServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeaderboardServiceBlockingStub(channel, callOptions);
        }
      };
    return LeaderboardServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LeaderboardServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LeaderboardServiceFutureStub>() {
        @java.lang.Override
        public LeaderboardServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LeaderboardServiceFutureStub(channel, callOptions);
        }
      };
    return LeaderboardServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getLeaderboard(com.wordy.proto.Empty request,
        io.grpc.stub.StreamObserver<com.wordy.proto.LeaderboardResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLeaderboardMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LeaderboardService.
   */
  public static abstract class LeaderboardServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LeaderboardServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LeaderboardService.
   */
  public static final class LeaderboardServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LeaderboardServiceStub> {
    private LeaderboardServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LeaderboardServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeaderboardServiceStub(channel, callOptions);
    }

    /**
     */
    public void getLeaderboard(com.wordy.proto.Empty request,
        io.grpc.stub.StreamObserver<com.wordy.proto.LeaderboardResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLeaderboardMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LeaderboardService.
   */
  public static final class LeaderboardServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LeaderboardServiceBlockingStub> {
    private LeaderboardServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LeaderboardServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeaderboardServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.wordy.proto.LeaderboardResponse getLeaderboard(com.wordy.proto.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLeaderboardMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LeaderboardService.
   */
  public static final class LeaderboardServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LeaderboardServiceFutureStub> {
    private LeaderboardServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LeaderboardServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LeaderboardServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.LeaderboardResponse> getLeaderboard(
        com.wordy.proto.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLeaderboardMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LEADERBOARD = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LEADERBOARD:
          serviceImpl.getLeaderboard((com.wordy.proto.Empty) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.LeaderboardResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetLeaderboardMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.Empty,
              com.wordy.proto.LeaderboardResponse>(
                service, METHODID_GET_LEADERBOARD)))
        .build();
  }

  private static abstract class LeaderboardServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LeaderboardServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.wordy.proto.Wordy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LeaderboardService");
    }
  }

  private static final class LeaderboardServiceFileDescriptorSupplier
      extends LeaderboardServiceBaseDescriptorSupplier {
    LeaderboardServiceFileDescriptorSupplier() {}
  }

  private static final class LeaderboardServiceMethodDescriptorSupplier
      extends LeaderboardServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LeaderboardServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LeaderboardServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LeaderboardServiceFileDescriptorSupplier())
              .addMethod(getGetLeaderboardMethod())
              .build();
        }
      }
    }
    return result;
  }
}
