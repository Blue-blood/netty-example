package com.example.nettydemo.ssltls.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 11:07
 */
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10000 ; i++) {
            new Thread(new clientRunnable()).start();
            Thread.sleep(10);
        }

    }

    public void run(String addr,int port) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap b= new Bootstrap();
            b.group(worker).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new EchoClientInitializer());
            ChannelFuture f =  b.connect(addr, port).sync();
            f.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
class clientRunnable implements Runnable{

    @Override
    public void run() {
        try {
//                    new EchoClient().run("127.0.0.1",6667);
            new EchoClient().run("122.51.235.98",6667);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
