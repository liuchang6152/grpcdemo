package com.liuchang.grpcclient.Client;

import com.liuchang.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CountDownLatch;
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
    private StudentServiceGrpc.StudentServiceStub studentServiceStub;
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


    /**
     * 请求是字符串，返回也是字符串
     **/
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
     * 请求是字符串，返回是流
     **/
    public List<Map> getStudentsByAge(int age) throws Exception {
        logger.info("Will try to greet " + age + " ...");
        List<Map> resultLsit = new ArrayList<>();
        StudentRequest request = StudentRequest.newBuilder().setAge(11).build();
        Iterator<StudentResponse> responses;
        try {
            if(channel.isShutdown()){
                this.afterPropertiesSet();
            }
            blockingStub=StudentServiceGrpc.newBlockingStub(channel);
            responses = blockingStub.getStudentsByAge(request);
            logger.info("Greeting: " + responses.toString());
           while(responses.hasNext()){
               StudentResponse studentResponse = responses.next();
               Map map = new HashMap();
               map.put("age",studentResponse.getAge());
               map.put("city",studentResponse.getCity());
               map.put("name",studentResponse.getName());
               resultLsit.add(map);
           }
            return resultLsit;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return resultLsit;
        } finally {
            try {
                shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 请求是流，返回是字符串
     **/
    public List<Map> getStudentsWrapperByAges(int age) throws Exception {
        logger.info("Will try to greet " + age + " ...");
        List<Map> resultLsit = new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        StudentRequest request = StudentRequest.newBuilder().setAge(11).build();
        StreamObserver<StudentResponseList> responseObserver =new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList studentResponseList) {

                List<StudentResponse> result = studentResponseList.getStudentResponseList();
                result.stream().forEach(studentResponse->{
                    Map map = new HashMap();
                    map.put("age",studentResponse.getAge());
                    map.put("city",studentResponse.getCity());
                    map.put("name",studentResponse.getName());
                    resultLsit.add(map);
                });

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                countDownLatch.countDown();
                try {
                    shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCompleted() {
                System.out.println("success");
                countDownLatch.countDown();
                try {
                    shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            if(channel.isShutdown()){
                this.afterPropertiesSet();
            }
            studentServiceStub=StudentServiceGrpc.newStub(channel);
            StreamObserver<StudentRequest> requestObserver = studentServiceStub.getStudentsWrapperByAges(responseObserver);
            requestObserver.onNext(request);
            requestObserver.onCompleted();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            shutdown();
            return resultLsit;
        }
        countDownLatch.await();
        shutdown();
        return resultLsit;
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
