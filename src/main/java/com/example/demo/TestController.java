package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/2 19:52
 */
@RestController
@RequestMapping("/hello2")
public class TestController {
    private StringBuffer strBuf = new StringBuffer();
    private URL pageUrl = null;

    @GetMapping("/test")
    public String test() {
        int i =0 ;
        while (true) {
            if (i>1)return "";
            Thread thread = new Thread(()->{
                requestRRR();
            });
            thread.start();
            Thread thread2 = new Thread(()->{
                requestRRR();
            });
            thread2.start();
//            return "success";
        }
    }
    public void requestRRR(){
        try {
            pageUrl = new URL("http://www.xbiquge.la/10/10489/");
            try {
                //获取网页的编码方式，这里可以解决乱码问题
                HttpURLConnection uc = (HttpURLConnection) pageUrl.openConnection();
                String encoding = uc.getContentType();
//                encoding = encoding.substring(encoding.indexOf("charset=") + 8).trim();
                // 创建网络流
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(pageUrl.openStream(), "utf-8"));
                String line;
                // 读取网页内容
                //new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    strBuf.append(line + "\t\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(strBuf.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
