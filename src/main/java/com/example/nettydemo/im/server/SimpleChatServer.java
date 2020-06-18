package com.example.nettydemo.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Desc:  启动服务器，然后分别启动多个client，连接到服务器  进行通信
 * @Author: MrDi
 * @Date: 2020/6/18 9:39
 */
public class SimpleChatServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            System.out.println("server启动了");

            ChannelFuture f =serverBootstrap.bind(port).sync();
            //等待服务器socket关闭
            f.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            System.out.println("server关闭了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new SimpleChatServer().run(6666);
    }
}
