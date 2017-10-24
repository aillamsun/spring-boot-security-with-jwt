package com.chinawiserv.core.exception;

import com.chinawiserv.core.enums.ErrorInfo;

/**
 * 微信相关异常
 * Created by sungang on 2017/9/22.
 */
public class WxException extends Exception {


    private ErrorInfo errorInfo;
    private Object[] args;

    public WxException(ErrorInfo errorInfo, Object... agrs) {
        this.errorInfo = errorInfo;
        this.args = agrs;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }


    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
