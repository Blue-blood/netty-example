package com.example.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/11 16:13
 */
public class DisCardClient {
    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("122.51.235.98",6666);
        SocketChannel socketChannel = null;
        ByteBuffer bf;
        try {
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()){}//如果没有完成连接就自旋,知道连接成功为止

            bf = ByteBuffer.allocate(1024);
            bf.put("这是一个小毛驴".getBytes());
            bf.put("我从来都不骑".getBytes());
            bf.flip();//转换为读模式
            socketChannel.write(bf);
            System.out.println(new String(bf.array()));

            bf.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketChannel.shutdownOutput();
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
