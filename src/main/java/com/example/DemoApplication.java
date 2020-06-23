package com.example;

import com.example.netty.netty.protobuf.ProtoServer;
import com.example.nettydemo.ssltls.server.EchoServer;
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
        new Thread(new WebSocketRunnable()).start();
        new Thread(new SSlEchoRunnable()).start();
        new ProtoServer(port).runServer();

    }
}

class WebSocketRunnable implements Runnable{

    @Override
    public void run() {
        try {
            new WebSocketServer().run(8099);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SSlEchoRunnable implements Runnable{

    @Override
    public void run() {
        try {
            new EchoServer().run(6667);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
