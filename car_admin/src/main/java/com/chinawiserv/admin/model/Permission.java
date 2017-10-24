package com.chinawiserv.admin.model;

import com.chinawiserv.core.model.BaseModel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sungang on 2017/10/24.
 */
@Table(name = "sys_user")
@Data
@ToString
public class Permission extends BaseModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private Long pid;

    private String method;

    @Column(name = "permission_url")
    private String permissionUrl;

    private String description;

    @Column(name = "create_time")
    private Date createTime;
}
