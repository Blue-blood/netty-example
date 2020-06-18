package com.example.nettydemo.websocketim.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 13:46
 */
public class WebsocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(64*1024))
                .addLast(new ChunkedWriteHandler())
                .addLast(new SimpleHttpRequestHandler("/ws"))
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(new TextSocketFrameHandler());

    }
}
