package com.chinawiserv.admin.model;

import com.chinawiserv.core.model.BaseModel;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by sungang on 2017/10/24.
 */
@Table(name = "sys_user")
@Data
@ToString
public class UserRole extends BaseModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name = "user_id")
    private Long userId;


    @Column(name = "role_id")
    private Long roleId;
}
