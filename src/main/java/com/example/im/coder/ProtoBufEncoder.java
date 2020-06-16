package com.example.im.coder;

import com.example.domain.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Desc: proto消息的编码器，接收的是Bean 会把消息前面加上 长度 魔数 版本号
 * @Author: MrDi
 * @Date: 2020/6/16 10:45
 */
public class ProtoBufEncoder extends MessageToByteEncoder<ProtoMsg.Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtoMsg.Message message, ByteBuf byteBuf) throws Exception {
        byte[] bytes =  message.toByteArray();
        int length = bytes.length;
        //将消息的长度写入
        //short占两个字节，最大长度32767，如果要更长 可以用int 或者long
        byteBuf.writeShort(length);
        //省略魔数版本号写入
        byteBuf.writeBytes(message.toByteArray());
    }
}
