package com.chinawiserv.admin.config.security;

import com.chinawiserv.admin.config.security.service.JwtUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by sungang on 2017/10/24.
 */
@Component
@Slf4j
public class CustomerAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("用户输入的用户名是：{}", authentication.getName());
        log.info("用户输入的密码是：{}", authentication.getCredentials());
        // 根据用户输入的用户名获取该用户名已经在服务器上存在的用户详情，如果没有则返回null
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        try {
            log.info("服务器上已经保存的用户名是：" + userDetails.getUsername());
            log.info("服务器上保存的该用户名对应的密码是： " + userDetails.getPassword());
            log.info("服务器上保存的该用户对应的权限是：" + userDetails.getAuthorities());
            //判断用户输入的密码和服务器上已经保存的密码是否一致
            if (authentication.getCredentials().equals(userDetails.getPassword())) {
                log.info("认证成功!");
                //如果验证通过，将返回一个UsernamePasswordAuthenticaionToken对象
                return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            }else{
                log.warn("认证失败, 密码输入不正确!");
                new BadCredentialsException("认证失败, 密码输入不正确!");
            }
        } catch (Exception e) {
            log.error("认证失败, error message is: " , e);
            throw e;
        }
        //如果验证不通过将抛出异常或者返回null
        return null;
    }



    //告诉身份验证的功能只能使用UsernamePasswordAuthenticationToken对象。
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
