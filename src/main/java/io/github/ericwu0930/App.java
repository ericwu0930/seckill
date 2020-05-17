package io.github.ericwu0930;

import io.github.ericwu0930.dao.UserMapper;
import io.github.ericwu0930.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = {"io.github.ericwu0930"})
@MapperScan("io.github.ericwu0930.dao")
@RestController
public class App 
{
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String home(){
        User user = userMapper.selectByPrimaryKey(1);
        if(user==null)
            return "用户对象不存在";
        else
            return user.getName();
    }
    public static void main( String[] args )
    {
        System.out.println("Hello,world!");
        SpringApplication.run(App.class,args);
    }
}
