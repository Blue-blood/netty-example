package com.example;

import com.example.netty.echo.EchoServerReactor;
import com.example.netty.netty.protobuf.ProtoServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(DemoApplication.class, args);
//        DiscardServer server = new DiscardServer();
//        server.test();
        int port = 6666;
        new ProtoServer(port).runServer();
    }
}
