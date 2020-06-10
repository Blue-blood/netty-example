package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/2 19:52
 */
@RestController
@RequestMapping("/security")
public class TestControllerSecurity {
    private StringBuffer strBuf = new StringBuffer();
    private URL pageUrl = null;

    @GetMapping("/test")
    public String test() {
       return "hello";
    }
}
