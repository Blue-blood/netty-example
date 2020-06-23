package com.example.nettydemo.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/22 9:34
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        readRequest(fullHttpRequest);
        String sendMsg;
        String uri = fullHttpRequest.uri();
        switch (uri){
            case "/":
                sendMsg = "<h3>123</h3>";
                break;
            case "/hi":
                sendMsg = "<h3>456</h3>";
                break;
            default:
                sendMsg = "<h3>default</h3>";
                break;
        }
        wirteResponse(channelHandlerContext,sendMsg);
    }

    private void wirteResponse(ChannelHandlerContext channelHandlerContext, String sendMsg) {
        ByteBuf buf = Unpooled.copiedBuffer(sendMsg,Charset.defaultCharset());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,sendMsg.length());
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html");
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }


    private void readRequest(FullHttpRequest fullHttpRequest) {
        System.out.println("请求行------");
        System.out.println(fullHttpRequest.method()+ " "+fullHttpRequest.uri()+ " "+ fullHttpRequest.protocolVersion());
        System.out.println("请求头---");
        for (String name :fullHttpRequest.headers().names()){
            System.out.println(name+":"+fullHttpRequest.headers().get(name));
        }
        System.out.println("消息体");
        System.out.println(fullHttpRequest.content().toString(Charset.defaultCharset()));
    }
}
