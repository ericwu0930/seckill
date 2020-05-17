package io.github.ericwu0930.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author erichwu
 * @date 2020/5/17
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    public ValidationResult validate(Object bean){
        final ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size()>0){
            validationResult.setHasErrors(true);
            constraintViolationSet.forEach(t->{
                String errMsg=t.getMessage();
                String propertyName=t.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName,errMsg);
            });

        }
        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        validator=Validation.buildDefaultValidatorFactory().getValidator();
    }
}
