package io.github.ericwu0930.dao;

import io.github.ericwu0930.pojo.User;
import io.github.ericwu0930.pojo.UserPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    int insert(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    int insertSelective(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    UserPassword selectByPrimaryKey(Integer id);

    UserPassword selectByUserId(Integer userId);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    int updateByPrimaryKeySelective(UserPassword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Fri May 15 15:46:33 CST 2020
     */
    int updateByPrimaryKey(UserPassword record);
}