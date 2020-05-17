package io.github.ericwu0930.error;

/**
 * @author erichwu
 * @date 2020/5/16
 */
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    // 直接接收EmBusinessError的传参用于构造异常异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError=commonError;
    }

    // 接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError=commonError;
        this.commonError.setErrorMsg(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errMsg) {
        this.commonError.setErrorMsg(errMsg);
        return this;
    }
}
