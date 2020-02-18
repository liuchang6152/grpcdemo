package com.liuchang.server;

import com.liuchang.proto.*;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 12:54 2020/2/17
 * @ Description：
 * @ Modified By：
 */
@Component
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        MyResponse reply = MyResponse.newBuilder().setRealname("张三").build();
        //接下来要做的事
        responseObserver.onNext(reply);
        //结束
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("server 接收端的数据"+request.getAge());
        StudentResponse studentResponse1 = StudentResponse.newBuilder().setAge(11).setCity("北京").setName("张三").build();
        responseObserver.onNext(studentResponse1);
        StudentResponse studentResponse2 = StudentResponse.newBuilder().setAge(12).setCity("北京").setName("李四").build();
        responseObserver.onNext(studentResponse2);
        StudentResponse studentResponse3 = StudentResponse.newBuilder().setAge(13).setCity("北京").setName("马武").build();
        responseObserver.onNext(studentResponse3);
        StudentResponse studentResponse4 = StudentResponse.newBuilder().setAge(15).setCity("北京").setName("刘六").build();
        responseObserver.onNext(studentResponse4);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest studentRequest) {
                System.out.println("--------------"+studentRequest.getAge()+"--------------");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse1 = StudentResponse.newBuilder().setAge(11).setCity("北京").setName("张三").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setAge(12).setCity("北京").setName("李四").build();
                StudentResponse studentResponse3 = StudentResponse.newBuilder().setAge(13).setCity("北京").setName("马武").build();
                StudentResponse studentResponse4 = StudentResponse.newBuilder().setAge(15).setCity("北京").setName("刘六").build();
                StudentResponseList studentResponseList = StudentResponseList.newBuilder().addStudentResponse(studentResponse1)
                        .addStudentResponse(studentResponse2)
                        .addStudentResponse(studentResponse3)
                        .addStudentResponse(studentResponse4).build();
                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }


}
