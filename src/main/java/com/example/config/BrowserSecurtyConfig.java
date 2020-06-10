package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/10 10:41
 */
@EnableWebSecurity
public class BrowserSecurtyConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("---------------------------");
        http.formLogin()
                .and().authorizeRequests()
                .antMatchers("/hello/*").permitAll()//允许这个路径通过
                .anyRequest().authenticated()//剩下的任何路径都校验
                .and().csrf().disable();//取消跨域
    }


}
