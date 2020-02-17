package com.liuchang.grpcclient.Client;

import com.liuchang.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：liuchang
 * @ Date       ：Created in 15:44 2020/2/17
 * @ Description：
 * @ Modified By：
 */
@Component
public class BaseClient  implements InitializingBean {
    @Value("${grpc.server.host}")
    public String host;
    @Value("${grpc.server.port}")
    public int port;
    public ManagedChannel channel;

    @Override
    public void afterPropertiesSet() throws Exception {
        ManagedChannelBuilder<?> channelBuilder= ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext();
        channel = channelBuilder.build();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
