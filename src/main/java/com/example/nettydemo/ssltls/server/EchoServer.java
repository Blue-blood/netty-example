package com.example.nettydemo.ssltls.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 10:58
 */
public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        int port = 6667;
        new EchoServer().run(port);
    }
    public void run(int port) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EchoServerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture f =  serverBootstrap.bind(port).sync();
            System.out.println("ssl服务器启动了");
            f.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
