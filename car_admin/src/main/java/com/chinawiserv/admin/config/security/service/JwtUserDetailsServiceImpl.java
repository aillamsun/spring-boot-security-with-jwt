package com.chinawiserv.admin.config.security.service;

import com.chinawiserv.admin.config.security.JwtUser;
import com.chinawiserv.admin.config.security.JwtUserFactory;
import com.chinawiserv.admin.mapper.UserMapper;
import com.chinawiserv.admin.model.User;
import com.chinawiserv.core.service.BaseService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl extends BaseService<User> implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", username);
        User user = userMapper.selectUserAndAuthorities(params);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            JwtUser userDetail =  JwtUserFactory.create(user);
            return userDetail;
        }
    }
}
