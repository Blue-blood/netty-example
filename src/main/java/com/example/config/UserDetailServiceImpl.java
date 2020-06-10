package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Author: wangdi
 * @Date: 2020/6/10 11:14
 */
@Configuration
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pw = passwordEncoder.encode("123455");
        User user = new User("user",pw, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        System.out.println(pw);
        return user;
    }
}
