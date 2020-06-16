//package com.example.im.client;
//
//import com.example.domain.ProtoMsg;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.util.AttributeKey;
//import lombok.Data;
//
///**
// * @Desc:
// * @Author: MrDi
// * @Date: 2020/6/16 14:30
// */
//@Data
//public class ClientSession {
//    private static final AttributeKey<ClientSession> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
//    //用户会话管理的核心
//    private Channel channel;
//    private User user;
//
//    //保存登录后的服务端sessionId
//    private String sessionId;
//
//    private Boolean isConnected = false ;
//    private Boolean isLogin = false;
//
//    //绑定通道  通道绑定这个session
//    private ClientSession(Channel channel){
//        this.channel = channel;
//        this.sessionId =String.valueOf(-1);
//        channel.attr(ClientSession.SESSION_KEY).set(this);
//    }
//    //登录成功后 设置seesionId
//    public static void loginSuccess(ChannelHandlerContext ctx, ProtoMsg.Message message){
//        ChannelHandlerContext.executor().schedule();
//        Channel channel  = ctx.channel();
//        ClientSession session =  channel.attr(ClientSession.SESSION_KEY).get();
//        session.setSessionId(message.getSessionId());
//        session.setIsLogin(true);
//        System.out.println("登录成功");
//    }
//    //获取session
//    public static ClientSession getSession(ChannelHandlerContext ctx){
//        Channel channel = ctx.channel();
//        return channel.attr(ClientSession.SESSION_KEY).get();
//    }
//
//    public String getRemoteAddress(){
//        return channel.remoteAddress().toString();
//    }
//
//    //把protobuf数据包写入通道
//    public ChannelFuture writeAndFlush(Object msg){
//        return channel.writeAndFlush(msg);
//    }
//
//    public void wirteAndClose(Object msg){
//        ChannelFuture future  =channel.writeAndFlush(msg);
//        future.addListener(ChannelFutureListener.CLOSE);
//    }
//    //关闭通道
//    public void close(){
//        isConnected =false;
//        ChannelFuture future =  channel.close();
//        future.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                if (future.isSuccess()){
//                    System.out.println("连接顺利断开");
//                }
//            }
//        });
//    }
//}
