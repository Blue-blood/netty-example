package com.example.netty.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {
    public static final NettyEchoClientHandler INSTANCE = new NettyEchoClientHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnitTime unitTime = (UnitTime) msg;
        System.out.println(unitTime.toString());
        ctx.close();
//        ByteBuf buf = (ByteBuf) msg;
//        int length  =  buf.readableBytes();
//        byte[] bytes = new byte[length];
//        buf.getBytes(0,bytes);
//        System.out.println("客户端接收内容:"+new String(bytes,"utf-8"));
        //释放ByteBuf的两种方法
        //1手动释放
        //buf.release();
        //2调用父类的入站方法，将msg向后传递.Netty会在最后释放掉buf
//        super.channelRead(ctx, msg);
    }
}
