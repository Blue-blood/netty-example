package com.example.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * @author Mrdi
 * @date 2020/6/13
 * @ChannelHandler.Sharable 注解表示此handler可以被多个通道的流水线共享
 */
@ChannelHandler.Sharable
public class NettyEchoHandler extends ChannelInboundHandlerAdapter {
    static final NettyEchoHandler INSTANCE = new NettyEchoHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        long time  = System.currentTimeMillis() / 1000L + 2208988800L;
//        ByteBuf byteBuf = ctx.alloc().buffer(4);
//        byteBuf.writeInt((int) time);
        ChannelFuture f = ctx.writeAndFlush(new UnitTime());
        //f.addListener(ChannelFutureListener.CLOSE);等价于下面的关闭上下文
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert f==channelFuture;
                ctx.close();
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("msg type: "+(in.hasArray()?"堆内存":"直接内存"));
        int length = in.readableBytes();
        byte[] arr = new byte[length];
        in.getBytes(0,arr);
        System.out.println("服务端接收数据为:"+new String(arr,"utf-8"));
        System.out.println("写回客户端前，msg的refCnt:"+((ByteBuf) msg).refCnt());
        //写回数据,异步任务
        ChannelFuture channelFuture =  ctx.writeAndFlush(msg);
        channelFuture.addListener((future)->{
            System.out.println("写回客户端后，msg的refCnt:"+((ByteBuf) msg).refCnt());
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
