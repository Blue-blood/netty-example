package com.example.nettydemo.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/22 9:44
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        /*  提供基于https的认证
        String pkPath = "ssl/nettyServer.jks";
//        String pkPath = System.getProperty("user.dir")+"classpath://ssl/nettyServer.jks";
        String password = "defaultPass";
        SSLEngine engine = SslContextFactory.getServerContext(pkPath,pkPath,password).createSSLEngine();
        engine.setUseClientMode(false);//设置为服务端模式
//        engine.setNeedClientAuth(true);//设置需要客户端验证

//        pipeline.addLast("ssl",new SslHandler(engine))
*/
        System.out.println("第"+count.getAndIncrement()+"个请求!!!");
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("codec",new HttpServerCodec())
                .addLast("aggregator",new HttpObjectAggregator(1048576))
                .addLast("serverHandler",new HttpServerHandler());
    }
}
