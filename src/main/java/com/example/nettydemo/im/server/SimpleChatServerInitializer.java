package com.example.nettydemo.im.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 9:35
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                .addLast("decoder",new StringDecoder())
                .addLast("encoder",new StringEncoder())
                .addLast("handler",new SimpleChatServerHandler());
        System.out.println("client:"+socketChannel.remoteAddress()+"连接上了！");
    }
}
