package com.example.nettydemo.websocketim.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import org.springframework.web.HttpRequestHandler;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 10:50
 */
public class SimpleHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUrl;
    private static final File INDEX;
    static {
        // TODO: 2020/6/18 这面获取文件的地址是不对的，要修改
        URL location =  HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("location:"+location.toString());
        try {
            String path = location.toURI()+ "/static/WebsocketChatClient.html";
            System.out.println(path);
            path=!path.contains("file:")?path:path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to locate WebsocketChatClient.html",e);
        }
    }

    public SimpleHttpRequestHandler(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (wsUrl.equalsIgnoreCase(fullHttpRequest.uri())){
            //如果请求是websocket请求 则计数器加1 调到下一个handler
            //retain() 引用计数器加1
            channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
        }else {
            if (HttpUtil.is100ContinueExpected(fullHttpRequest)){
                send100Continue(channelHandlerContext);
            }
            RandomAccessFile file =  new RandomAccessFile(INDEX,"r");
            HttpResponse response  = new DefaultFullHttpResponse(fullHttpRequest.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set( HttpHeaders.Names.CONTENT_TYPE,"text/html;charset=UTF-8");
            Boolean keepAlive =  HttpUtil.isKeepAlive(fullHttpRequest);
            if (keepAlive){
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            }
            channelHandlerContext.write(response);
            if (channelHandlerContext.pipeline().get(SslHandler.class)==null){
                channelHandlerContext.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
            }else {
                channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));
            }
            ChannelFuture future =  channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive){
                future.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();

        }
    }

    private void send100Continue(ChannelHandlerContext channelHandlerContext) {
        FullHttpResponse response =  new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
        channelHandlerContext.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
