package com.example.nettydemo.websocketim.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Desc:   启动springboot项目，启动WebsocketServer，请求http://localhost:9999/WebsocketChatClient.html页面 开始聊天
 * @Author: MrDi
 * @Date: 2020/6/18 13:52
 */
public class WebSocketServer {

    public void run(int port) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebsocketInitializer())
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
        new WebSocketServer().run(6666);
    }
}
