package com.example.nettydemo.ssltls.server;

import com.example.nettydemo.ssltls.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 10:49
 */
public class EchoServerInitializer extends ChannelInitializer<SocketChannel> {
    AtomicInteger i  =new AtomicInteger(0);
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        String pkPath = "ssl/nettyServer.jks";
//        String pkPath = System.getProperty("user.dir")+"classpath://ssl/nettyServer.jks";
        String password = "defaultPass";
        SSLEngine engine = SslContextFactory.getServerContext(pkPath,pkPath,password).createSSLEngine();
        engine.setUseClientMode(false);//设置为服务端模式
        engine.setNeedClientAuth(true);//设置需要客户端验证
        pipeline.addLast(new SslHandler(engine))
                .addLast(new EchoServerHandler());
        System.out.println("i="+i.getAndAdd(1));
    }
}
