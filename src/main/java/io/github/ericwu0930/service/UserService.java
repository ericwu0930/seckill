package io.github.ericwu0930.service;

import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.service.model.UserModel;

/**
 * @author erichwu
 * @date 2020/5/15
 */
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String password) throws BusinessException;

}
