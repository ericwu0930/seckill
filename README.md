1. 关于枚举类 包装器业务异常类实现 （使用了装饰者模式）
https://www.cnblogs.com/panchanggui/p/10318368.html
2. 设计了validator，通过注解来实现，关注一下
3. mybatis-generator如何出现在插件里 https://www.cnblogs.com/itzyz/p/10978553.html
4. 在mapper.xml中使用useGeneratedKeys="true" keyProperty="id" 使得其支持自增长，即pojo可以得到自增长的id值
5. 看一下mybatis的上锁操作 select for update
6. 事务aop 如何实现类内方法的事务回滚 https://www.cnblogs.com/wlwl/p/10092494.html
7. DateTime类
8. 关于pluginManagement https://blog.csdn.net /joenqc/article/details/54910629
9. 实在排查不出问题的时候重新编译一下
10. maven的各个生命周期依赖于各种plugin，要对springboot打包要把这个包含在其中
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```
11. nginx配置 https://www.jianshu.com/p/d3fb148cb5eb