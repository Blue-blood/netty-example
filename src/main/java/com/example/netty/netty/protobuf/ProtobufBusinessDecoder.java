package com.example.netty.netty.protobuf;

import com.example.ProtoDemo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class ProtobufBusinessDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ProtoDemo.Student student = (ProtoDemo.Student) msg;
        System.out.print("收到一个ProtoDemo.Student数据包 =》");
        System.out.print("     id="+student.getId());
        System.out.print("     email="+student.getEmail());
        System.out.println("");
        super.channelRead(ctx, msg);
    }
}
