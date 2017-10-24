package com.chinawiserv.admin.mapper;

import com.chinawiserv.admin.model.User;
import com.chinawiserv.core.dao.mybatis.BaseMapper;

import java.util.Map;

/**
 * Created by sungang on 2017/10/24.
 */
public interface UserMapper extends BaseMapper<User> {

    User selectUserAndAuthorities(Map<String,Object> params);
}
