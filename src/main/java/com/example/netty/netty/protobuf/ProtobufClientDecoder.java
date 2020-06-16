package com.example.netty.netty.protobuf;

import com.example.ProtoDemo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class ProtobufClientDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtoDemo.Student student = (ProtoDemo.Student) msg;
        System.out.print("收到一个ProtoDemo.Student数据包 =》");
        System.out.print("     id="+student.getId());
        System.out.print("     email="+student.getEmail());
        System.out.println("");

        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel =  ctx.channel();
        if (channel.isActive()) channel.close();
        super.exceptionCaught(ctx, cause);
    }
}
