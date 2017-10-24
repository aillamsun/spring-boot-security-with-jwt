package com.chinawiserv.admin.model;

import com.chinawiserv.core.model.BaseModel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by sungang on 2017/10/24.
 */
@Table(name = "sys_user")
@Data
@ToString
public class User extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private Integer status;

    private String remark;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;

    @Transient
    private List<Permission> authorities;

    @Transient
    private Long roleId;
}
