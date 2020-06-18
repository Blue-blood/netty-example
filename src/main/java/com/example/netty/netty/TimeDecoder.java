package com.example.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class TimeDecoder extends ByteToMessageDecoder {

    public static final TimeDecoder INSTANCE = new TimeDecoder();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //前面搞个拦截的，小于4个字节就直接返回不处理，来的话  超过4个字节  就只读4个字节发给下一个 解决了粘包问题
        if (byteBuf.readableBytes()<4) return;
//        list.add(byteBuf.readBytes(4));
        list.add(new UnitTime(byteBuf.readUnsignedInt()));
    }
}
