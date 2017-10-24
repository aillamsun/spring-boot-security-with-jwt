package com.chinawiserv.admin.config.security.url;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by sungang on 2017/10/24.
 */
@Data
@AllArgsConstructor
public class UrlGrantedAuthority implements GrantedAuthority {

    private String name;
    private String permissionUrl;
    private String method;


    @Override
    public String getAuthority() {
        return this.name + ";" + this.permissionUrl + ";" + this.method;
    }
}
