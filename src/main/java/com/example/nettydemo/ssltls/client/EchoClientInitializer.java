package com.example.nettydemo.ssltls.client;

import com.example.nettydemo.ssltls.SslContextFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 10:54
 */
public class EchoClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        String caPath  = "ssl/nettyClient.jks";
//        String caPath  = System.getProperty("user.dir")+"/src/main/resources/ssl/nettyClient.jks";
        String password = "defaultPass";
        SSLEngine engine =  SslContextFactory.getClientContext(caPath,caPath,password).createSSLEngine();
        engine.setNeedClientAuth(true);//需要客户端验证
        engine.setUseClientMode(true);//客户端模式
        //sslHandler一定要放在最前面
        pipeline.addLast(new SslHandler(engine))
                .addLast(new EchoClientHandler());

    }
}
