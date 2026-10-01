package com.wordy.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: wordy.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AdminServiceGrpc {

  private AdminServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "wordy.AdminService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.Player,
      com.wordy.proto.Status> getCreatePlayerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePlayer",
      requestType = com.wordy.proto.Player.class,
      responseType = com.wordy.proto.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.Player,
      com.wordy.proto.Status> getCreatePlayerMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.Player, com.wordy.proto.Status> getCreatePlayerMethod;
    if ((getCreatePlayerMethod = AdminServiceGrpc.getCreatePlayerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getCreatePlayerMethod = AdminServiceGrpc.getCreatePlayerMethod) == null) {
          AdminServiceGrpc.getCreatePlayerMethod = getCreatePlayerMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.Player, com.wordy.proto.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePlayer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Player.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Status.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("CreatePlayer"))
              .build();
        }
      }
    }
    return getCreatePlayerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.Player,
      com.wordy.proto.Status> getUpdatePlayerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdatePlayer",
      requestType = com.wordy.proto.Player.class,
      responseType = com.wordy.proto.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.Player,
      com.wordy.proto.Status> getUpdatePlayerMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.Player, com.wordy.proto.Status> getUpdatePlayerMethod;
    if ((getUpdatePlayerMethod = AdminServiceGrpc.getUpdatePlayerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getUpdatePlayerMethod = AdminServiceGrpc.getUpdatePlayerMethod) == null) {
          AdminServiceGrpc.getUpdatePlayerMethod = getUpdatePlayerMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.Player, com.wordy.proto.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdatePlayer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Player.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Status.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("UpdatePlayer"))
              .build();
        }
      }
    }
    return getUpdatePlayerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.PlayerId,
      com.wordy.proto.Status> getDeletePlayerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeletePlayer",
      requestType = com.wordy.proto.PlayerId.class,
      responseType = com.wordy.proto.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.PlayerId,
      com.wordy.proto.Status> getDeletePlayerMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.PlayerId, com.wordy.proto.Status> getDeletePlayerMethod;
    if ((getDeletePlayerMethod = AdminServiceGrpc.getDeletePlayerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getDeletePlayerMethod = AdminServiceGrpc.getDeletePlayerMethod) == null) {
          AdminServiceGrpc.getDeletePlayerMethod = getDeletePlayerMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.PlayerId, com.wordy.proto.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeletePlayer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.PlayerId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Status.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("DeletePlayer"))
              .build();
        }
      }
    }
    return getDeletePlayerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.SearchQuery,
      com.wordy.proto.PlayerList> getSearchPlayerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchPlayer",
      requestType = com.wordy.proto.SearchQuery.class,
      responseType = com.wordy.proto.PlayerList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.SearchQuery,
      com.wordy.proto.PlayerList> getSearchPlayerMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.SearchQuery, com.wordy.proto.PlayerList> getSearchPlayerMethod;
    if ((getSearchPlayerMethod = AdminServiceGrpc.getSearchPlayerMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getSearchPlayerMethod = AdminServiceGrpc.getSearchPlayerMethod) == null) {
          AdminServiceGrpc.getSearchPlayerMethod = getSearchPlayerMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.SearchQuery, com.wordy.proto.PlayerList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SearchPlayer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.SearchQuery.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.PlayerList.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("SearchPlayer"))
              .build();
        }
      }
    }
    return getSearchPlayerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.Config,
      com.wordy.proto.Status> getUpdateConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateConfig",
      requestType = com.wordy.proto.Config.class,
      responseType = com.wordy.proto.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.Config,
      com.wordy.proto.Status> getUpdateConfigMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.Config, com.wordy.proto.Status> getUpdateConfigMethod;
    if ((getUpdateConfigMethod = AdminServiceGrpc.getUpdateConfigMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getUpdateConfigMethod = AdminServiceGrpc.getUpdateConfigMethod) == null) {
          AdminServiceGrpc.getUpdateConfigMethod = getUpdateConfigMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.Config, com.wordy.proto.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Config.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Status.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("UpdateConfig"))
              .build();
        }
      }
    }
    return getUpdateConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.wordy.proto.Empty,
      com.wordy.proto.Config> getGetConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConfig",
      requestType = com.wordy.proto.Empty.class,
      responseType = com.wordy.proto.Config.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.wordy.proto.Empty,
      com.wordy.proto.Config> getGetConfigMethod() {
    io.grpc.MethodDescriptor<com.wordy.proto.Empty, com.wordy.proto.Config> getGetConfigMethod;
    if ((getGetConfigMethod = AdminServiceGrpc.getGetConfigMethod) == null) {
      synchronized (AdminServiceGrpc.class) {
        if ((getGetConfigMethod = AdminServiceGrpc.getGetConfigMethod) == null) {
          AdminServiceGrpc.getGetConfigMethod = getGetConfigMethod =
              io.grpc.MethodDescriptor.<com.wordy.proto.Empty, com.wordy.proto.Config>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.wordy.proto.Config.getDefaultInstance()))
              .setSchemaDescriptor(new AdminServiceMethodDescriptorSupplier("GetConfig"))
              .build();
        }
      }
    }
    return getGetConfigMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdminServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminServiceStub>() {
        @java.lang.Override
        public AdminServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminServiceStub(channel, callOptions);
        }
      };
    return AdminServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdminServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminServiceBlockingStub>() {
        @java.lang.Override
        public AdminServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminServiceBlockingStub(channel, callOptions);
        }
      };
    return AdminServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AdminServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AdminServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AdminServiceFutureStub>() {
        @java.lang.Override
        public AdminServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AdminServiceFutureStub(channel, callOptions);
        }
      };
    return AdminServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createPlayer(com.wordy.proto.Player request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePlayerMethod(), responseObserver);
    }

    /**
     */
    default void updatePlayer(com.wordy.proto.Player request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdatePlayerMethod(), responseObserver);
    }

    /**
     */
    default void deletePlayer(com.wordy.proto.PlayerId request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeletePlayerMethod(), responseObserver);
    }

    /**
     */
    default void searchPlayer(com.wordy.proto.SearchQuery request,
        io.grpc.stub.StreamObserver<com.wordy.proto.PlayerList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchPlayerMethod(), responseObserver);
    }

    /**
     */
    default void updateConfig(com.wordy.proto.Config request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateConfigMethod(), responseObserver);
    }

    /**
     */
    default void getConfig(com.wordy.proto.Empty request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Config> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConfigMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AdminService.
   */
  public static abstract class AdminServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AdminServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AdminService.
   */
  public static final class AdminServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AdminServiceStub> {
    private AdminServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminServiceStub(channel, callOptions);
    }

    /**
     */
    public void createPlayer(com.wordy.proto.Player request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePlayerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updatePlayer(com.wordy.proto.Player request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdatePlayerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deletePlayer(com.wordy.proto.PlayerId request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePlayerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchPlayer(com.wordy.proto.SearchQuery request,
        io.grpc.stub.StreamObserver<com.wordy.proto.PlayerList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchPlayerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateConfig(com.wordy.proto.Config request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConfig(com.wordy.proto.Empty request,
        io.grpc.stub.StreamObserver<com.wordy.proto.Config> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AdminService.
   */
  public static final class AdminServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AdminServiceBlockingStub> {
    private AdminServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.wordy.proto.Status createPlayer(com.wordy.proto.Player request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePlayerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.wordy.proto.Status updatePlayer(com.wordy.proto.Player request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdatePlayerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.wordy.proto.Status deletePlayer(com.wordy.proto.PlayerId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePlayerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.wordy.proto.PlayerList searchPlayer(com.wordy.proto.SearchQuery request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchPlayerMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.wordy.proto.Status updateConfig(com.wordy.proto.Config request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.wordy.proto.Config getConfig(com.wordy.proto.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConfigMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AdminService.
   */
  public static final class AdminServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AdminServiceFutureStub> {
    private AdminServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdminServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AdminServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.Status> createPlayer(
        com.wordy.proto.Player request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePlayerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.Status> updatePlayer(
        com.wordy.proto.Player request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdatePlayerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.Status> deletePlayer(
        com.wordy.proto.PlayerId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePlayerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.PlayerList> searchPlayer(
        com.wordy.proto.SearchQuery request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchPlayerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.Status> updateConfig(
        com.wordy.proto.Config request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.wordy.proto.Config> getConfig(
        com.wordy.proto.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_PLAYER = 0;
  private static final int METHODID_UPDATE_PLAYER = 1;
  private static final int METHODID_DELETE_PLAYER = 2;
  private static final int METHODID_SEARCH_PLAYER = 3;
  private static final int METHODID_UPDATE_CONFIG = 4;
  private static final int METHODID_GET_CONFIG = 5;

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
        case METHODID_CREATE_PLAYER:
          serviceImpl.createPlayer((com.wordy.proto.Player) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.Status>) responseObserver);
          break;
        case METHODID_UPDATE_PLAYER:
          serviceImpl.updatePlayer((com.wordy.proto.Player) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.Status>) responseObserver);
          break;
        case METHODID_DELETE_PLAYER:
          serviceImpl.deletePlayer((com.wordy.proto.PlayerId) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.Status>) responseObserver);
          break;
        case METHODID_SEARCH_PLAYER:
          serviceImpl.searchPlayer((com.wordy.proto.SearchQuery) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.PlayerList>) responseObserver);
          break;
        case METHODID_UPDATE_CONFIG:
          serviceImpl.updateConfig((com.wordy.proto.Config) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.Status>) responseObserver);
          break;
        case METHODID_GET_CONFIG:
          serviceImpl.getConfig((com.wordy.proto.Empty) request,
              (io.grpc.stub.StreamObserver<com.wordy.proto.Config>) responseObserver);
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
          getCreatePlayerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.Player,
              com.wordy.proto.Status>(
                service, METHODID_CREATE_PLAYER)))
        .addMethod(
          getUpdatePlayerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.Player,
              com.wordy.proto.Status>(
                service, METHODID_UPDATE_PLAYER)))
        .addMethod(
          getDeletePlayerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.PlayerId,
              com.wordy.proto.Status>(
                service, METHODID_DELETE_PLAYER)))
        .addMethod(
          getSearchPlayerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.SearchQuery,
              com.wordy.proto.PlayerList>(
                service, METHODID_SEARCH_PLAYER)))
        .addMethod(
          getUpdateConfigMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.Config,
              com.wordy.proto.Status>(
                service, METHODID_UPDATE_CONFIG)))
        .addMethod(
          getGetConfigMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.wordy.proto.Empty,
              com.wordy.proto.Config>(
                service, METHODID_GET_CONFIG)))
        .build();
  }

  private static abstract class AdminServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AdminServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.wordy.proto.Wordy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AdminService");
    }
  }

  private static final class AdminServiceFileDescriptorSupplier
      extends AdminServiceBaseDescriptorSupplier {
    AdminServiceFileDescriptorSupplier() {}
  }

  private static final class AdminServiceMethodDescriptorSupplier
      extends AdminServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AdminServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AdminServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdminServiceFileDescriptorSupplier())
              .addMethod(getCreatePlayerMethod())
              .addMethod(getUpdatePlayerMethod())
              .addMethod(getDeletePlayerMethod())
              .addMethod(getSearchPlayerMethod())
              .addMethod(getUpdateConfigMethod())
              .addMethod(getGetConfigMethod())
              .build();
        }
      }
    }
    return result;
  }
}
