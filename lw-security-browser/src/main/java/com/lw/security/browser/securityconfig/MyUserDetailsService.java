package com.lw.security.browser.securityconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 登陆处理
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    /**
     * 使用密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名："+username);
        //根据用户名查找用户信息
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码："+password);
        return new User(username, password,
                true, true, true, true,
                        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
