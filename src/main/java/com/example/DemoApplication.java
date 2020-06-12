package com.example;

import com.example.netty.echo.EchoServerReactor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(DemoApplication.class, args);
//        DiscardServer server = new DiscardServer();
//        server.test();
        new Thread(new EchoServerReactor()).start();
    }
}
