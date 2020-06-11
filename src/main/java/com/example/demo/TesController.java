package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/2 19:52
 */
@RestController
@RequestMapping("/hello")
public class TesController {

    @GetMapping("/world")
    public testVo world(){
        testVo vo = new testVo();
        vo.setAge(18);
        vo.setName("小黑");
        vo.setSex("男");
        return vo;
    }

    @PostMapping("/world2")
    public testVo world2(@RequestBody testVo vo){
        System.out.println(vo);
        vo.setName("張三");
        vo.setAge(19);
        vo.setSex("女");
        return vo;
    }

    @GetMapping("/1")
    public void sssssss(){
        System.out.println("--------------");
    }

    @GetMapping("/test")
    public String test(){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        // 以表单的方式提交
        headers.setContentType(MediaType.TEXT_HTML);
        List list = new ArrayList();
        list.add(Charset.forName("utf-8"));
        headers.setAcceptCharset(list);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = client.exchange("http://www.xbiquge.la/10/10489/", method, requestEntity, String.class);
        HttpHeaders h = response.getHeaders();
        h.setAcceptCharset(list);
        String result = response.getBody();
        System.out.println(result);
        return "success";
    }
}
