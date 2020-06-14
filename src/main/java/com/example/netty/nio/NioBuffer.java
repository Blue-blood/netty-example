package com.example.netty.nio;

import java.nio.IntBuffer;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/11 14:09
 */
public class NioBuffer {

        static IntBuffer intBuffer =null;

        public static void allocatTest(){
            intBuffer = IntBuffer.allocate(20);
            System.out.println(intBuffer.capacity());
            System.out.println(intBuffer.limit());
            System.out.println(intBuffer.position());
            intBuffer.put(1);
            intBuffer.put(2);
            intBuffer.put(3);
            System.out.println(intBuffer.capacity());
            System.out.println(intBuffer.limit());
            System.out.println(intBuffer.position());
            intBuffer.flip();
            System.out.println(intBuffer.capacity());
            System.out.println(intBuffer.limit());
            System.out.println(intBuffer.position());
//            intBuffer.get();
            intBuffer.flip();//写模式变为读模式
            intBuffer.clear();//读模式变为写模式
            intBuffer.compact();//读模式变为写模式
            intBuffer.rewind();//读完一遍重复读
            System.out.println(intBuffer.capacity());
            System.out.println(intBuffer.limit());
            System.out.println(intBuffer.position());
        }

    public static void main(String[] args) {
        allocatTest();
    }
}
