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
public class DisCardClient implements Runnable {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            new Thread(new DisCardClient()).start();
        }
    }


    @Override
    public void run() {
//                SocketAddress address = new InetSocketAddress("122.51.235.98",6666);
        SocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        SocketChannel socketChannel = null;
        ByteBuffer bf= ByteBuffer.allocate(1024);
        try {
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()) {
            }//如果没有完成连接就自旋,知道连接成功为止

            bf.put("这是一个小毛驴".getBytes());
            bf.put("我从来都不骑".getBytes());
            bf.put((System.currentTimeMillis()+"").getBytes());
            bf.flip();//转换为读模式
            socketChannel.write(bf);
            Thread.sleep(500);
            bf.clear();
            socketChannel.read(bf);
            System.out.println(new String(bf.array()));

//            //重置bf
//            bf.clear();
//            Thread.sleep(1000);
//            bf.put("再来一遍".getBytes());
//            bf.flip();//转换为读模式
//            socketChannel.write(bf);
//            Thread.sleep(500);
//            bf.clear();
//            socketChannel.read(bf);
//            System.out.println(new String(bf.array()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketChannel!=null){
                    socketChannel.shutdownOutput();
                    socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
