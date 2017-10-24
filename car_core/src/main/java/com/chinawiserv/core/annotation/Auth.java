package com.chinawiserv.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sungang on 2016/8/31.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    boolean verifyLogin() default false; //验证登录

    boolean verifyWxAuth() default false; //验证企业认证

    boolean verifyAppLogin() default false; //验证用户金额
}
