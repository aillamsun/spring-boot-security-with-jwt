package com.chinawiserv.admin.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.chinawiserv.admin.config.security.JwtAuthenticationRequest;
import com.chinawiserv.admin.config.security.JwtTokenUtil;
import com.chinawiserv.admin.config.security.JwtUser;
import com.chinawiserv.admin.mapper.UserMapper;
import com.chinawiserv.admin.mapper.UserRoleMapper;
import com.chinawiserv.admin.model.User;
import com.chinawiserv.admin.model.UserRole;
import com.chinawiserv.core.enums.GlobalErrorInfoEnum;
import com.chinawiserv.core.exception.GlobalErrorInfoException;
import com.chinawiserv.core.response.ResultBody;
import com.chinawiserv.core.response.ResultGenerator;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by sungang on 2017/10/24.
 */
@RestController
@RequestMapping("/api/login")
public class AuthenticationController {


    @Value("${jwt.header}")
    private String tokenHeader;



    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     *
     * @param userToAdd
     * @return
     * @throws GlobalErrorInfoException
     */
    @PostMapping("register")
    public ResultBody register(User userToAdd) throws GlobalErrorInfoException {
        final String username = userToAdd.getUsername();
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", username);
        if (userMapper.selectUserAndAuthorities(params) != null) {
            return ResultGenerator.genFailResult(GlobalErrorInfoEnum.INTERNAL_SERVER_ERROR.getCode(),"改用户名已经存在!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userToAdd.setEmail("");
        userToAdd.setStatus(1);
        userMapper.insert(userToAdd);


        /**
         * 用户角色关系
         */
        UserRole userRole = new UserRole();
        userRole.setRoleId(userToAdd.getRoleId());
        userRole.setUserId(userToAdd.getId());
        userRoleMapper.insert(userRole);


        return ResultGenerator.genSuccessResult();
    }



    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping
    public ResultBody createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        // Return the token
        JSONObject res = new JSONObject();
        res.put("access_token",token);
        res.put("authorities",userDetails.getAuthorities());
        return ResultGenerator.genSuccessResult(res);
    }



    @RequestMapping("403")
    public ResultBody access403(String url, HttpServletResponse response) throws AuthenticationException {
        response.setStatus(403);
        return ResultGenerator.genFailResult(GlobalErrorInfoEnum.INTERNAL_SERVER_ERROR.getCode(),"没有权限访问：" + url);
    }

    /**
     * @param request
     * @return
     */
    @GetMapping(value = "refresh")
    public ResultBody refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResultGenerator.genSuccessResult(refreshedToken);
        } else {
            return ResultGenerator.genSuccessResult();
        }
    }
}
