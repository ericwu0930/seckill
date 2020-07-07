package io.github.ericwu0930.controller;

import io.github.ericwu0930.controller.vo.UserVO;
import io.github.ericwu0930.error.BusinessException;
import io.github.ericwu0930.error.EmBusinessError;
import io.github.ericwu0930.pojo.User;
import io.github.ericwu0930.response.CommonReturnType;
import io.github.ericwu0930.service.UserService;
import io.github.ericwu0930.service.model.UserModel;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.descriptor.web.MessageDestinationRef;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author erichwu
 * @date 2020/5/15
 */

@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*") //跨域访问 是session可以共享
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

    // 用户登录接口
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone, @RequestParam(name = "password") String password) throws Exception {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telephone, EncodeByMD5(password));
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        redisTemplate.opsForValue().set(uuid, userModel);
        redisTemplate.expire(uuid, 1, TimeUnit.HOURS);
//        // 将登录凭证加入到用户登陆成功的session内
//        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(uuid);
    }

    //用户注册接口
    @ResponseBody
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone, @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name, @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws Exception {
        System.out.println("进入register");
        // 验证手机号和对应的optcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不正确");
        }

        // 用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(EncodeByMD5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    // 加密
    private String EncodeByMD5(String str) throws Exception {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return encode;
    }


    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUSer(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用service服务获取对应的id的用户对象并且返回到前端
        UserModel userById = userService.getUserById(id);

        if (userById == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        UserVO userVO = convertFromModel(userById);
        return CommonReturnType.create(userVO);
    }


    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone") String telephone) {
        // 需要按照一定的规则，生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        // 将opt验证码从对应用户的手机号关联，使用httpsession的方式绑定他的手机号与optcode
        httpServletRequest.getSession().setAttribute(telephone, optCode);

        // 将opt验证码通过短信通道发送给用户，省略
        System.out.println("telephone=" + telephone + "&optCode=" + optCode);

        return CommonReturnType.create(null);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null)
            return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
