package com.chinawiserv.core.enums;

/**
 * Created by sungang on 2017/5/19.
 */
public enum ErrorInfoEnum implements ErrorInfo {

    USER_OR_PWD_NOT_NUlL("000001", "用户名或密码不能为空！"),
    USER_JWT_EXPIRED("000002", "Token不能为空或失效了!"),
    USER_JWT_FORMAT("000003", "Token格式不正确!"),
    CAPTCHA_ERROR("000004", "验证码不正确!"),
    PHONE_CODE_ERROR("000005", "手机短信码不正确!"),
    OPEN_ID_IS_NOT_NULL("000006", "!"),
    OPEN_ID_EXPIRED("000007", "openId过期重新授权!"),
    NOT_MEMBER("000008", "当前不是会员用户!"),
    CAPTCHA_MARK_ERROR("000009", "验证码标记不正确或者验证码已过期!"),
    PHONE_SEND_CODE_ERROR("000010", "发送手机短信码失败!");


    private String code;

    private String message;

    ErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
