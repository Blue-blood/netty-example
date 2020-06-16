package com.example;

import com.example.netty.netty.protobuf.ProtoServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {
//  疯狂创客圈源码
//    https://gitee.com/qq2821003945/netty_redis_zookeeper_source_code/tree/master

    public static void main(String[] args) throws IOException {

        SpringApplication.run(DemoApplication.class, args);
//        DiscardServer server = new DiscardServer();
//        server.test();
        int port = 6666;
        new ProtoServer(port).runServer();
    }
}
