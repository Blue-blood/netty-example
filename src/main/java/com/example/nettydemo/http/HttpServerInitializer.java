package com.example.nettydemo.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/22 9:44
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("codec",new HttpServerCodec())
                .addLast("aggregator",new HttpObjectAggregator(1048576))
                .addLast("serverHandler",new HttpServerHandler());
    }
}
