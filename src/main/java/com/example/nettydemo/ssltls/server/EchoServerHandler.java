package com.example.nettydemo.ssltls.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/19 10:37
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf  = (ByteBuf) msg;
        System.out.println("server收到:"+buf.toString(CharsetUtil.UTF_8));
        ByteBuf b = Unpooled.buffer();
        b.writeBytes(("我收到了").getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(b);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
