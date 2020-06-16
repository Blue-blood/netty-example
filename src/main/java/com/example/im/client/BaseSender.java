//package com.example.im.client;
//
//import com.example.domain.ProtoMsg;
//import io.netty.channel.ChannelFuture;
//import io.netty.util.concurrent.Future;
//import io.netty.util.concurrent.GenericFutureListener;
//import lombok.Data;
//
///**
// * @Desc:
// * @Author: MrDi
// * @Date: 2020/6/16 14:27
// */
//@Data
//public abstract class BaseSender {
//    private User user;
//    private ClientSession clientSession;
//
//    public ClientSession getSession(){
//        return this.clientSession;
//
//    }
//    public Boolean isConnected(){
//        return clientSession.getIsConnected();
//    }
//
//    public void sendMsg(ProtoMsg.Message msg ){
//        if (getSession()==null||!isConnected()){
//            System.out.println("连接还没成功");
//        }
//        ChannelFuture future = getSession().writeAndFlush(msg);
//        future.addListener(new GenericFutureListener<Future<? super Void>>() {
//            @Override
//            public void operationComplete(Future<? super Void> future) throws Exception {
//                if (future.isSuccess()){
//                    System.out.println("发送成功");
//                }else {
//                    System.out.println("发送失败");
//                }
//            }
//        });
//
//    }
//}
