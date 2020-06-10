package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
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
        http.userDetailsService(userDetailsService).formLogin().and().authorizeRequests()
        .anyRequest().authenticated().and().csrf().disable();
    }


}
