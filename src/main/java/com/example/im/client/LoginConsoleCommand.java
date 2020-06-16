package com.example.im.client;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * @Desc: 负责从scanner控制台收集客户端登录的账号密码
 * @Author: MrDi
 * @Date: 2020/6/16 11:31
 */
@Data
@Service
public class LoginConsoleCommand implements BaseCommand {
    public static final String KEY = "1";
    private String userName;
    private String password;

    public void exec(Scanner scanner){
        System.out.println("请用户输入账号密码(id:password)");
        String[] info =null;
        while (true){
            String input = scanner.next();
            info =  input.split(":");
            if (info.length!=2){
                System.out.println("格式不正确");
            }else {
                break;
            }
        }
        userName = info[0];
        password = info[1];
    }

    public String getKey(){
        return KEY;
    }

    public String getTip(){
        return "登录";
    }
}
