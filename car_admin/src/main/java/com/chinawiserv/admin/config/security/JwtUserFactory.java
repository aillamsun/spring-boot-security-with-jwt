package com.chinawiserv.admin.config.security;

import java.util.List;
import java.util.stream.Collectors;

import com.chinawiserv.admin.config.security.url.UrlGrantedAuthority;
import com.chinawiserv.admin.model.Permission;
import com.chinawiserv.admin.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus() == 0 ? false : true,
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Permission> authorities) {
        return authorities.stream().map(authority -> new UrlGrantedAuthority(authority.getName(),authority.getPermissionUrl(), authority.getMethod())).collect(Collectors.toList());
    }
}
