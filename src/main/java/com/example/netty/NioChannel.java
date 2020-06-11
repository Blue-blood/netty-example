package com.example.netty;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/11 14:40
 */
public class NioChannel {
    public static void main(String[] args) {
        testChannel();
    }
    //复制文件
    private static void testChannel() {
        FileInputStream is = null;
        FileOutputStream os = null;
        FileChannel inChannel =  null;
        FileChannel outChannel =null;
        try {
             is = new FileInputStream("D:\\test\\123.txt");
             os = new FileOutputStream("D:\\test\\456.txt");
             inChannel =  is.getChannel();
             outChannel = os.getChannel();
            int length =-1;
            ByteBuffer bf = ByteBuffer.allocate(1024);//创建buffer
            while((length = inChannel.read(bf))!=-1){//读取channel内容写入到buffer中
                System.out.println(length);
                bf.flip();//把buffer变为读模式

                int outLength = 0;
                while ((outLength = outChannel.write(bf))!=0){//读取buffer中的内容写入到outChannel中
                    System.out.println(outLength);
                }
                bf.clear();//清空bf，重置为写模式
            }
            outChannel.force(true);//强制刷新到磁盘
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
