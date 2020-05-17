package io.github.ericwu0930.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author erichwu
 * @date 2020/5/17
 */
public class ValidationResult {
    private boolean hasErrors;

    private Map<String,String> errorMsgMap=new HashMap<>();

    public boolean isHasErrors(){
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors){
        this.hasErrors=hasErrors;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray());
    }
}
