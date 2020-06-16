//package com.example.im.client;
//
//import com.example.domain.ProtoMsg;
//import org.springframework.stereotype.Service;
//
///**
// * @Desc:
// * @Author: MrDi
// * @Date: 2020/6/16 14:27
// */
//@Service
//public class LoginSender extends BaseSender {
//    public void serndLoginMsg(){
//        if (!isConnected()){
//            System.out.println("还没有建立连接");
//        }
//        System.out.println("生成登录消息");
//        ProtoMsg.Message.Builder builder =     ProtoMsg.Message.newBuilder();
//        builder.setType(ProtoMsg.HeadType.LOGIN_REQUEST);
//        // TODO: 2020/6/16 这面没有设置属性值
//        ProtoMsg.Message message = builder.build();
//        super.sendMsg(message);
//    }
//}
