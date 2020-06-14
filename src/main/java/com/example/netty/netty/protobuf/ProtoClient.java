package com.example.netty.netty.protobuf;

import com.example.ProtoDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author Mrdi
 * @date 2020/6/14
 */
public class ProtoClient {

    private int serverPort;
    private String serverAddress;
    Bootstrap bootstrap = new Bootstrap();

    public ProtoClient(int serverPort, String serverAddress) {
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
                    socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender())//解决半包问题
                            .addLast(new ProtobufEncoder());//把proto对象转成二进制
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

            //发送proto对象
            for (int i = 1; i < 100000; i++) {
                ProtoDemo.Student student =  build(i,i+".com邮箱");
                channel.writeAndFlush(student);
                System.out.println("发送报文数:"+i);
            }
            channel.flush();
            Thread.sleep(Integer.MAX_VALUE);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workLoopGroup.shutdownGracefully();
        }
    }

    public ProtoDemo.Student build(int id,String email){
        ProtoDemo.Student.Builder builder = ProtoDemo.Student.newBuilder();
        builder.setId(id);
        builder.setEmail(email);
        return builder.build();
    }

    public static void main(String[] args) {
        ProtoClient client  = new ProtoClient(6666,"122.51.235.98");

//        ProtoClient client  = new ProtoClient(6666,"127.0.0.1");
        client.runClient();
    }
}
