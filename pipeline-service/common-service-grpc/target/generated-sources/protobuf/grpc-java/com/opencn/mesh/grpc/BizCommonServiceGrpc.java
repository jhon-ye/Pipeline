package com.opencn.mesh.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.47.0)",
    comments = "Source: PipelineTrafficEntrance.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BizCommonServiceGrpc {

  private BizCommonServiceGrpc() {}

  public static final String SERVICE_NAME = "com.opencn.mesh.grpc.BizCommonService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.opencn.mesh.grpc.PipelineRequest,
      com.opencn.mesh.grpc.PipelineResponse> getReqMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "req",
      requestType = com.opencn.mesh.grpc.PipelineRequest.class,
      responseType = com.opencn.mesh.grpc.PipelineResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.opencn.mesh.grpc.PipelineRequest,
      com.opencn.mesh.grpc.PipelineResponse> getReqMethod() {
    io.grpc.MethodDescriptor<com.opencn.mesh.grpc.PipelineRequest, com.opencn.mesh.grpc.PipelineResponse> getReqMethod;
    if ((getReqMethod = BizCommonServiceGrpc.getReqMethod) == null) {
      synchronized (BizCommonServiceGrpc.class) {
        if ((getReqMethod = BizCommonServiceGrpc.getReqMethod) == null) {
          BizCommonServiceGrpc.getReqMethod = getReqMethod =
              io.grpc.MethodDescriptor.<com.opencn.mesh.grpc.PipelineRequest, com.opencn.mesh.grpc.PipelineResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "req"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.opencn.mesh.grpc.PipelineRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.opencn.mesh.grpc.PipelineResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BizCommonServiceMethodDescriptorSupplier("req"))
              .build();
        }
      }
    }
    return getReqMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BizCommonServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceStub>() {
        @java.lang.Override
        public BizCommonServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BizCommonServiceStub(channel, callOptions);
        }
      };
    return BizCommonServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BizCommonServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceBlockingStub>() {
        @java.lang.Override
        public BizCommonServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BizCommonServiceBlockingStub(channel, callOptions);
        }
      };
    return BizCommonServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BizCommonServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BizCommonServiceFutureStub>() {
        @java.lang.Override
        public BizCommonServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BizCommonServiceFutureStub(channel, callOptions);
        }
      };
    return BizCommonServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BizCommonServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void req(com.opencn.mesh.grpc.PipelineRequest request,
        io.grpc.stub.StreamObserver<com.opencn.mesh.grpc.PipelineResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReqMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.opencn.mesh.grpc.PipelineRequest,
                com.opencn.mesh.grpc.PipelineResponse>(
                  this, METHODID_REQ)))
          .build();
    }
  }

  /**
   */
  public static final class BizCommonServiceStub extends io.grpc.stub.AbstractAsyncStub<BizCommonServiceStub> {
    private BizCommonServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BizCommonServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BizCommonServiceStub(channel, callOptions);
    }

    /**
     */
    public void req(com.opencn.mesh.grpc.PipelineRequest request,
        io.grpc.stub.StreamObserver<com.opencn.mesh.grpc.PipelineResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BizCommonServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BizCommonServiceBlockingStub> {
    private BizCommonServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BizCommonServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BizCommonServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.opencn.mesh.grpc.PipelineResponse req(com.opencn.mesh.grpc.PipelineRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BizCommonServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BizCommonServiceFutureStub> {
    private BizCommonServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BizCommonServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BizCommonServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.opencn.mesh.grpc.PipelineResponse> req(
        com.opencn.mesh.grpc.PipelineRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BizCommonServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BizCommonServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQ:
          serviceImpl.req((com.opencn.mesh.grpc.PipelineRequest) request,
              (io.grpc.stub.StreamObserver<com.opencn.mesh.grpc.PipelineResponse>) responseObserver);
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

  private static abstract class BizCommonServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BizCommonServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.opencn.mesh.grpc.PipelineTrafficEntrance.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BizCommonService");
    }
  }

  private static final class BizCommonServiceFileDescriptorSupplier
      extends BizCommonServiceBaseDescriptorSupplier {
    BizCommonServiceFileDescriptorSupplier() {}
  }

  private static final class BizCommonServiceMethodDescriptorSupplier
      extends BizCommonServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BizCommonServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (BizCommonServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BizCommonServiceFileDescriptorSupplier())
              .addMethod(getReqMethod())
              .build();
        }
      }
    }
    return result;
  }
}
