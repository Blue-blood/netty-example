package com.example;

import com.example.nettydemo.websocketim.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
//  疯狂创客圈源码
//    https://gitee.com/qq2821003945/netty_redis_zookeeper_source_code/tree/master

    public static void main(String[] args) throws Exception{

        SpringApplication.run(DemoApplication.class, args);
//        DiscardServer server = new DiscardServer();
//        server.test();
        int port = 6666;
//        new EchoServer(port).runServer();
//        new ProtoServer(port).runServer();
        new WebSocketServer().run(8099);
    }
}
