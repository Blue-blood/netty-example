package com.example.nettydemo.websocketim.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 11:28
 */
public class TextSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:"+ctx.channel().remoteAddress()+"在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:"+ctx.channel().remoteAddress()+"离开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client:"+ctx.channel().remoteAddress()+"异常");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel =  ctx.channel();
        channel.writeAndFlush(new TextWebSocketFrame("[SERVER - ]"+channel.remoteAddress()+"加入"));
        //保存到通道组中，方便通知其他channel
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel  =ctx.channel();
        channel.writeAndFlush(new TextWebSocketFrame("[SERVER - ]"+channel.remoteAddress()+"离开"));
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
//        channels.remove(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("打印"+textWebSocketFrame.text());
        System.out.println("计数器是:"+textWebSocketFrame.refCnt());
        Channel in  =channelHandlerContext.channel();
        for(Channel channel:channels){
            if (channel!=in){
                channel.writeAndFlush(new TextWebSocketFrame(String.format("[%s]%s\n",in.remoteAddress(),textWebSocketFrame.text())));
            }else {
                channel.writeAndFlush(new TextWebSocketFrame(String.format("[you]%s\n",textWebSocketFrame.text())));
            }
        }

    }
}
