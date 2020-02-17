package com.liuchang.grpcclient.Client;

import com.liuchang.proto.MyRequest;
import com.liuchang.proto.MyResponse;
import com.liuchang.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 14:15 2020/2/17
 * @ Description：
 * @ Modified By：
 */
@Component
public class StudentClient extends BaseClient {
    private static final Logger logger = Logger.getLogger(StudentClient.class.getName());
    private StudentServiceGrpc.StudentServiceBlockingStub blockingStub;
    public StudentClient(){
       // blockingStub=StudentServiceGrpc.newBlockingStub(channel);
    }
//    public StudentClient(){
//
//    }
    /** Construct client connecting to HelloWorld server at {@code host:port}. */
//    public StudentClient(String host,int port) {
//        this.host = host;
//        this.port = port;
//        ManagedChannelBuilder<?> channelBuilder= ManagedChannelBuilder.forAddress(host, port)
//                .usePlaintext();
//        channel = channelBuilder.build();
//        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
//    }

//    /** Construct client for accessing RouteGuide server using the existing channel. */
//    StudentClient(ManagedChannelBuilder<?> channelBuilder) {
//        channel = channelBuilder.build();
//        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
//    }


    /** Say hello to server. */
    public String getRealNameByUsername(String name) {
        logger.info("Will try to greet " + name + " ...");
        MyRequest request = MyRequest.newBuilder().setUsername(name).build();
        MyResponse response;
        try {
            if(blockingStub==null){
                blockingStub=StudentServiceGrpc.newBlockingStub(channel);
            }
            response = blockingStub.getRealNameByUsername(request);
            logger.info("Greeting: " + response.getRealname());
            return response.getRealname();
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return "";
        } finally {
            try {
                shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


/**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
//    public static void main(String[] args) throws Exception {
//        MyClient client = new MyClient("localhost", 50051);
//        try {
//            /* Access a service running on the local machine on port 50051 */
//            String user = "world";
//            if (args.length > 0) {
//                user = args[0]; /* Use the arg as the name to greet if provided */
//            }
//            client.greet(user);
//        } finally {
//            client.shutdown();
//        }
//    }



}
