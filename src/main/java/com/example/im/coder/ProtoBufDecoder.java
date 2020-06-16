package com.example.im.coder;

import com.example.domain.ProtoMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Desc: protobuf的解码器，把二进制数据 解码成bean
 * @Author: MrDi
 * @Date: 2020/6/16 11:09
 */
public class ProtoBufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //标记下读指针的位置
        byteBuf.markReaderIndex();
        //长度还不够包头  直接退出
        if (byteBuf.readableBytes()<2){
            return;
        }
        //读取传过来的信息的长度
        int length  = byteBuf.readShort();
        //非法长度  直接关闭连接
        if (length<0){
            channelHandlerContext.close();
        }
        //如果传进来的长度小于可读长度，重置读取位置，因为前面读取了一个short
        if (length>byteBuf.readableBytes()){
            byteBuf.resetReaderIndex();
            return;
        }
        //bytes 存储读到的实际的message
        byte[] bytes;
        if (byteBuf.hasArray()){
            //数据存在堆缓冲区
            ByteBuf slice =  byteBuf.slice();
            bytes = slice.array();
        }else {
            //数据存在直接缓冲区
            bytes = new byte[length];
            byteBuf.readBytes(bytes,0,length);
        }

        ProtoMsg.Message message = ProtoMsg.Message.parseFrom(bytes);
        if (message!=null){
            list.add(message);
        }


    }
}
