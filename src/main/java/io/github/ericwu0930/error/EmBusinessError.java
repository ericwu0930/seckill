package io.github.ericwu0930.error;

/**
 * @author erichwu
 * @date 2020/5/16
 */
public enum EmBusinessError implements CommonError{
    // 通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    UNKNOWN_ERROR(10002,"未知错误"),

    // 10000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),

    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确");

    private EmBusinessError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrorCode() {
        return errCode;
    }

    @Override
    public String getErrorMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;

    }
}
