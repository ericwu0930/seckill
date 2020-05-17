package io.github.ericwu0930.service.impl;

import io.github.ericwu0930.dao.UserMapper;
import io.github.ericwu0930.dao.UserPasswordMapper;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.pojo.User;
import io.github.ericwu0930.pojo.UserPassword;
import io.github.ericwu0930.service.UserService;
import io.github.ericwu0930.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author erichwu
 * @date 2020/5/15
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    @Transactional // 事务标签
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        if(StringUtils.isEmpty(userModel.getName())||
                userModel.getGender()==null
                ||userModel.getAge()==null
                ||StringUtils.isEmpty(userModel.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        User user = convertFromModel(userModel);
        userMapper.insertSelective(user);

        UserPassword userPassword=convertPasswordFromModel(userModel,user);
        userPasswordMapper.insertSelective(userPassword);
    }

    private UserPassword convertPasswordFromModel(UserModel userModel,User user) {
        if (userModel == null)
            return null;
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        userPassword.setUserId(user.getId());
        return userPassword;
    }

    private User convertFromModel(UserModel userModel){
        if(userModel==null)
            return null;
        User user = new User();
        BeanUtils.copyProperties(userModel,user);

        return user;
    }

    @Override
    public UserModel getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user==null)
            return null;
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());
        UserModel userModel = convertFromDataObject(user, userPassword);
        return userModel;
    }

    private UserModel convertFromDataObject(User user, UserPassword userPassword){
        if(user==null)
            return null;
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(user,userModel);

        if(userPassword==null)
            return null;
        userModel.setEncrptPassword(userPassword.getEncrptPassword());

        return userModel;
    }
}
