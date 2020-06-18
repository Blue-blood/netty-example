package com.example.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class NettyEchoClient {

    private int serverPort;
    private String serverAddress;
    Bootstrap bootstrap = new Bootstrap();

    public NettyEchoClient(int serverPort, String serverAddress) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }
    public void runClient(){
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();
        try {
            //1设置反应器线程组
            bootstrap.group(workLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(serverAddress,serverPort);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                            .addLast(TimeDecoder.INSTANCE)
                            .addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture channelFuture =  bootstrap.connect();
            channelFuture.addListener(future -> {
                if (future.isSuccess()){
                    System.out.println("客户端连接成功");
                }else {
                    System.out.println("客户端连接失败");
                }
            });
            //阻塞 直到连接成功
            channelFuture.sync();
            Channel channel =  channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入要发送的内容");
            while (scanner.hasNext()){
                String msg = scanner.next();
                byte[] bytes = (msg+System.currentTimeMillis()).getBytes("utf-8");
                //创建一个回写的buf
                ByteBuf buf =channel.alloc().buffer();
                System.out.println("写回服务端前，msg的refCnt:"+buf.refCnt());
                buf.writeBytes(bytes);
                ChannelFuture channe =channel.writeAndFlush(buf);
                channe.addListener((future)->{
                    System.out.println("写回服务端后，msg的refCnt:"+buf.refCnt());
                });
                System.out.println("请输入要发送的内容");
                //写回数据,异步任务

            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            workLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
//        NettyEchoClient client  = new NettyEchoClient(6666,"127.0.0.1");
        NettyEchoClient client  = new NettyEchoClient(6666,"122.51.235.98");
        client.runClient();
    }
}
