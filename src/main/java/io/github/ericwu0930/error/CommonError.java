package io.github.ericwu0930.error;

/**
 * @author erichwu
 * @date 2020/5/16
 */
public interface CommonError {
    public int getErrorCode();
    public String getErrorMsg();
    public CommonError setErrorMsg(String errMsg);
}
