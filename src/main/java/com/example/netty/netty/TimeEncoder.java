package com.example.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/17 14:40
 */
//public class TimeEncoder extends ChannelOutboundHandlerAdapter {
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        UnitTime time = (UnitTime) msg;
//        ByteBuf byteBuf = ctx.alloc().buffer(4);
//        byteBuf.writeInt((int) time.value());
//        ctx.write(byteBuf,promise);
//    }
//}
    //比上面的方法要简化很多
public class TimeEncoder extends MessageToByteEncoder<UnitTime> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, UnitTime unitTime, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt((int) unitTime.value());
    }
}