package com.example.netty.netty.protobuf;

import com.example.ProtoDemo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author Mrdi
 * @date 2020/6/13
 */
public class ProtoServer {
    private final int serverPort;
    ServerBootstrap bootstrap = new ServerBootstrap();

    public ProtoServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        //创建反应器线程组
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            //1设置反应器线程组
            bootstrap.group(bossLoopGroup, workerLoopGroup);
            //2设置nio类型的通道
            bootstrap.channel(NioServerSocketChannel.class);
            //5装配子通道流水线
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时，会创建一个通道
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //流水线管理子通道中的handler处理器
                    //向子通道流水线  添加一个 处理器
                    socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                    .addLast(new ProtobufDecoder(ProtoDemo.Student.getDefaultInstance()))
                    .addLast(new ProtobufBusinessDecoder());

                }
            });
            //3设置监听端口
            bootstrap.localAddress(serverPort);
            //4设置通道的参数
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            //6开始绑定服务器
            //通过调用sync同步方法阻塞，直到绑定成功
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println("服务器启动成功，监听端口:"+channelFuture.channel().localAddress());
            //7等待通道关闭的异步任务结束
            //服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture =  channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //8关闭EventLoopGroup
            //释放掉所有的资源，包括创建的线程
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }

    }
    public static void main(String[] args) {
        int port = 6666;
        new ProtoServer(port).runServer();
    }
}
