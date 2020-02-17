package com.liuchang.server;

import com.liuchang.proto.MyRequest;
import com.liuchang.proto.MyResponse;
import com.liuchang.proto.StudentServiceGrpc;
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
}
