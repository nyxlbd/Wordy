package com.wordy.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: wordy.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GameServiceGrpc {

  private GameServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "wordy.GameService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.JoinGameRequest,
      com.wordy.proto.JoinGameResponse> getJoinGameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "JoinGame",
      requestType = com.wordy.proto.JoinGameRequest.class,
      responseType = com.wordy.proto.JoinGameResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.JoinGameRequest,
      com.wordy.proto.JoinGameResponse> getJoinGameMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.JoinGameRequest, com.wordy.proto.JoinGameResponse> getJoinGameMethod;
    if ((getJoinGameMethod = GameServiceGrpc.getJoinGameMethod) == null) {
      synchronized (GameServiceGrpc.class) {
        if ((getJoinGameMethod = GameServiceGrpc.getJoinGameMethod) == null) {
          GameServiceGrpc.getJoinGameMethod = getJoinGameMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.JoinGameRequest, com.wordy.proto.JoinGameResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "JoinGame"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.JoinGameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.JoinGameResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameServiceMethodDescriptorSupplier("JoinGame"))
              .build();
        }
      }
    }
    return getJoinGameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.WordRequest,
      com.wordy.proto.WordResponse> getSubmitWordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitWord",
      requestType = com.wordy.proto.WordRequest.class,
      responseType = com.wordy.proto.WordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.WordRequest,
      com.wordy.proto.WordResponse> getSubmitWordMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.WordRequest, com.wordy.proto.WordResponse> getSubmitWordMethod;
    if ((getSubmitWordMethod = GameServiceGrpc.getSubmitWordMethod) == null) {
      synchronized (GameServiceGrpc.class) {
        if ((getSubmitWordMethod = GameServiceGrpc.getSubmitWordMethod) == null) {
          GameServiceGrpc.getSubmitWordMethod = getSubmitWordMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.WordRequest, com.wordy.proto.WordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitWord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.WordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.WordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GameServiceMethodDescriptorSupplier("SubmitWord"))
              .build();
        }
      }
    }
    return getSubmitWordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.GameRequest,
      com.wordy.proto.GameUpdate> getStreamGameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamGame",
      requestType = com.wordy.proto.GameRequest.class,
      responseType = com.wordy.proto.GameUpdate.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.wordy.proto.GameRequest,
      com.wordy.proto.GameUpdate> getStreamGameMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.GameRequest, com.wordy.proto.GameUpdate> getStreamGameMethod;
    if ((getStreamGameMethod = GameServiceGrpc.getStreamGameMethod) == null) {
      synchronized (GameServiceGrpc.class) {
        if ((getStreamGameMethod = GameServiceGrpc.getStreamGameMethod) == null) {
          GameServiceGrpc.getStreamGameMethod = getStreamGameMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.GameRequest, com.wordy.proto.GameUpdate>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamGame"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.GameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.GameUpdate.getDefaultInstance()))
              .setSchemaDescriptor(new GameServiceMethodDescriptorSupplier("StreamGame"))
              .build();
        }
      }
    }
    return getStreamGameMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GameServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameServiceStub>() {
        @java.lang.Override
        public GameServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameServiceStub(channel, callOptions);
        }
      };
    return GameServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GameServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameServiceBlockingStub>() {
        @java.lang.Override
        public GameServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameServiceBlockingStub(channel, callOptions);
        }
      };
    return GameServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GameServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GameServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GameServiceFutureStub>() {
        @java.lang.Override
        public GameServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GameServiceFutureStub(channel, callOptions);
        }
      };
    return GameServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void joinGame(com.wordy.proto.JoinGameRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.JoinGameResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinGameMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client sends words while game is ongoing
     * </pre>
     */
    default void submitWord(com.wordy.proto.WordRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.WordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitWordMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server streams game updates to client
     * </pre>
     */
    default void streamGame(com.wordy.proto.GameRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.GameUpdate> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamGameMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service GameService.
   */
  public static abstract class GameServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GameServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service GameService.
   */
  public static final class GameServiceStub
      extends io.grpc.stub.AbstractAsyncStub<GameServiceStub> {
    private GameServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameServiceStub(channel, callOptions);
    }

    /**
     */
    public void joinGame(com.wordy.proto.JoinGameRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.JoinGameResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinGameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client sends words while game is ongoing
     * </pre>
     */
    public void submitWord(com.wordy.proto.WordRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.WordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitWordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Server streams game updates to client
     * </pre>
     */
    public void streamGame(com.wordy.proto.GameRequest request,
        io.grpc.stub.StreamObserver<com.wordy.proto.GameUpdate> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamGameMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service GameService.
   */
  public static final class GameServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GameServiceBlockingStub> {
    private GameServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.wordy.proto.JoinGameResponse joinGame(com.wordy.proto.JoinGameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinGameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Client sends words while game is ongoing
     * </pre>
     */
    public com.wordy.proto.WordResponse submitWord(com.wordy.proto.WordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitWordMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Server streams game updates to client
     * </pre>
     */
    public java.util.Iterator<com.wordy.proto.GameUpdate> streamGame(
        com.wordy.proto.GameRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamGameMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service GameService.
   */
  public static final class GameServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<GameServiceFutureStub> {
    private GameServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GameServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GameServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.JoinGameResponse> joinGame(
        com.wordy.proto.JoinGameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinGameMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Client sends words while game is ongoing
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.WordResponse> submitWord(
        com.wordy.proto.WordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitWordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_JOIN_GAME = 0;
  private static final int METHODID_SUBMIT_WORD = 1;
  private static final int METHODID_STREAM_GAME = 2;

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
        case METHODID_JOIN_GAME:
          serviceImpl.joinGame((com.wordy.proto.JoinGameRequest) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.JoinGameResponse>) responseObserver);
          break;
        case METHODID_SUBMIT_WORD:
          serviceImpl.submitWord((com.wordy.proto.WordRequest) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.WordResponse>) responseObserver);
          break;
        case METHODID_STREAM_GAME:
          serviceImpl.streamGame((com.wordy.proto.GameRequest) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.GameUpdate>) responseObserver);
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
          getJoinGameMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.JoinGameRequest,
              com.wordy.proto.JoinGameResponse>(
                service, METHODID_JOIN_GAME)))
        .addMethod(
          getSubmitWordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.WordRequest,
              com.wordy.proto.WordResponse>(
                service, METHODID_SUBMIT_WORD)))
        .addMethod(
          getStreamGameMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.wordy.proto.GameRequest,
              com.wordy.proto.GameUpdate>(
                service, METHODID_STREAM_GAME)))
        .build();
  }

  private static abstract class GameServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GameServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.wordy.proto.Wordy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GameService");
    }
  }

  private static final class GameServiceFileDescriptorSupplier
      extends GameServiceBaseDescriptorSupplier {
    GameServiceFileDescriptorSupplier() {}
  }

  private static final class GameServiceMethodDescriptorSupplier
      extends GameServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GameServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (GameServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GameServiceFileDescriptorSupplier())
              .addMethod(getJoinGameMethod())
              .addMethod(getSubmitWordMethod())
              .addMethod(getStreamGameMethod())
              .build();
        }
      }
    }
    return result;
  }
}
