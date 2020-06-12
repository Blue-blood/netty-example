package com.example.netty.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/12 9:53
 */
public class EchoHandler implements Runnable{

    final SocketChannel socketChannel;
    final SelectionKey sk;
    final ByteBuffer bf = ByteBuffer.allocate(1024);

    static final int RECOEVING = 0,SENDING =1;
    int state =RECOEVING;

    public EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        sk = this.socketChannel.register(selector,0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state ==SENDING){
                //写入通道
                socketChannel.write(bf);
                //写完后准备从通道读，bf变为写模式
                bf.clear();
                //写完后，注册read就绪事件
                sk.interestOps(SelectionKey.OP_READ);
                state =RECOEVING;
            }else if (state ==RECOEVING){
                sk.interestOps(0);
                //从通道读
                int length =0;
//                System.out.println(socketChannel.read(bf));
//                System.out.println(new String(bf.array()));
                while ((length=socketChannel.read(bf))>0){
                    System.out.println(new String(bf.array()));
                    //写入bf后，bf变为读模式
                    bf.put("我收到了".getBytes());
                    bf.flip();
                    //读完后 注册write就绪事件
                    sk.interestOps(SelectionKey.OP_WRITE);
                    //读完后  进入发送状态
                    state = SENDING;
                }

            }
            //处理结束后，这里不能关闭selectkey，需要重复使用
//            sk.cancel();
        }catch (Exception e){
            sk.cancel();
            e.printStackTrace();
        }
    }
}
