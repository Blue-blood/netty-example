package com.example.netty.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/12 9:52
 */
public class EchoServerReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;

    public EchoServerReactor() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //将新链接处理器放到key上
        sk.attach(new AcceptorHandler());
    }

    @Override
    public void run() {
        //选择器轮询
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectionKeySet =  selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    dispatch(key);
                }
                selectionKeySet.clear();
            }
        } catch (IOException e) {
            System.out.println("链接断开");
        }

    }

    private void dispatch(SelectionKey key) {
        Runnable handler = (Runnable) key.attachment();
        if (handler!=null){
            handler.run();
        }
    }

    class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new EchoHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
