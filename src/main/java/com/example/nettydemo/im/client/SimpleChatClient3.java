package com.example.nettydemo.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 9:56
 */
public class SimpleChatClient3 {
    public void run(String addr,int port) throws InterruptedException, IOException {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());

            ChannelFuture f= bootstrap.connect(addr,port).sync();
            Channel channel = f.channel();
            BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
            while (true){
                channel.writeAndFlush(in.readLine()+"\r\n");
            }
        }finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new SimpleChatClient3().run("127.0.0.1",6666);
    }
}
