package com.example.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/10 14:04
 */
public class MyAuthentivationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        userDetailsService.loadUserByUsername(authentication.getName());
        System.out.println("权限验证。。。");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
