package com.example.nettydemo.im.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 9:24
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
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
        channel.writeAndFlush("[SERVER - ]"+channel.remoteAddress()+"加入");
        //保存到通道组中，方便通知其他channel
        channels.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel  =ctx.channel();
        channel.writeAndFlush("[SERVER - ]"+channel.remoteAddress()+"离开");
        channels.remove(channel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("打印"+s);
        Channel in  =channelHandlerContext.channel();
        for(Channel channel:channels){
            if (channel!=in){
                channel.writeAndFlush(String.format("[%s]%s\n",in.remoteAddress(),s));
            }else {
                channel.writeAndFlush(String.format("[you]%s\n",s));
            }
        }
    }
}
