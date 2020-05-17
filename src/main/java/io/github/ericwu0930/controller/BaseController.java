package io.github.ericwu0930.controller;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author erichwu
 * @date 2020/5/16
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";



    // 定义exceptionhandler解决未被controller曾吸收的exception 只能返回页面路径
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){

        Map<String,Object> responseData=new HashMap<>();

        if (ex instanceof BusinessException) {
            BusinessException businessException=(BusinessException)ex;
            responseData.put("errCode",businessException.getErrorCode());
            responseData.put("errMsg",businessException.getErrorMsg());
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
