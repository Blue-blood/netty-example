//package com.example.im.client;
//
//import com.example.domain.ProtoMsg;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Scanner;
//
///**
// * @Desc: 获取到用户账号密码之后，组成User POJO对象，然后通过客户端发送器LoginSender向服务器发送登录请求
// * @Author: MrDi
// * @Date: 2020/6/16 11:36
// */
//@Service
//public class CommonClient {
//    private BaseCommand baseCommand;
//    private Boolean connectFlag =false;
//    private LoginSender loginSender;
//
//    //命令收集线程
//    public void startCommond(){
//        Thread.currentThread().setName("主线程");
//        while (true){
//            while (connectFlag == false){
//                startConnectServer();
//                waitCommandThread();
//            }
//            //处理命令
//            while (session!=null&&session.isConnected()){
//                Scanner scanner = new Scanner(System.in);
//                loginConsoleCommand.exec(scanner);
//                String key = loginConsoleCommand.getCommandInput();
//                //取到命令收集类POJO
//                BaseCommand baseCommand = commandMap.get(key);
//                switch (key){
//                    case LoginConsoleCommand.KEY:
//                        baseCommand.exec(scanner);
//                        startLogin((LoginConsoleCommand)baseCommand);
//                        break;
//                    // TODO: 2020/6/16 省略其他命令
//                }
//            }
//        }
//    }
//    //开始发送登录请求
//    private void startLogin(LoginConsoleCommand command){
//        if (!isConnectFlag()){
//            System.out.println("连接异常，请重新连接");
//            return;
//        }
//        User user = new User();
//        user.setUid(command.getUserName());
//        user.setTocken(command.getPassword());
//        user.setDevId("1111");
//        loginSender.setUser(user);
//        loginSender.setClientSession(session);
//        loginSender.serndLoginMsg();
//    }
//
//
//}
