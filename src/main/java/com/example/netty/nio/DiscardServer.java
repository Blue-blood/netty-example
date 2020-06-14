package com.example.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/11 15:52
 */
public class DiscardServer {
    public void test() {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector =null;
        try {
            selector =  Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);//设置为非阻塞
            serverSocketChannel.bind(new InetSocketAddress(6666));//绑定监听端口
            System.out.println("服务器启动成功");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//把通道注册到选择器，并且监听 接收新链接 状态
            while (selector.select()>0){
                Set<SelectionKey> selectionKeySet =  selector.selectedKeys();
                Iterator<SelectionKey> iterator =  selectionKeySet.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey =  iterator.next();
                    if (selectionKey.isAcceptable()){
                        //如果接收到了新链接，开启客户端连接通道
                        SocketChannel clientChannel =  serverSocketChannel.accept();
                        clientChannel.configureBlocking(false);//非阻塞
                        clientChannel.register(selector,SelectionKey.OP_READ);//把客户端通道注册到选择器，并且监听 可读 状态
                    }else if(selectionKey.isReadable()){
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        try {
                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            int length = 0;
                            while ((length = clientChannel.read(bf))>0){
                                bf.flip();//转换为读状态
                                System.out.println(new String(bf.array()));
                                bf.clear();//重置，并且转化为写状态
                                bf.put("我收到了".getBytes());
                                clientChannel.write(bf);
                            }
                        }finally {
                            clientChannel.shutdownOutput();
                            clientChannel.close();
                        }
                    }
                    //处理完选择键 ，移除选择键
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocketChannel.close();
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
