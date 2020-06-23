package com.example.nettydemo.im.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 9:50
 */
@ChannelHandler.Sharable//表示多个ChannelPipeline可以共享同一个ChannelHandler。
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}
