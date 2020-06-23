package com.example.netty.netty.protobuf;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/16 17:54
 */
public class Client implements Runnable {
    @Override
    public void run() {
//        ProtoClient client  = new ProtoClient(6666,"122.51.235.98");
        ProtoClient client  = new ProtoClient(6666,"127.0.0.1");
        client.runClient();
    }
}
