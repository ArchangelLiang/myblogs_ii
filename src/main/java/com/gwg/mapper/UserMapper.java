package com.gwg.mapper;

import com.gwg.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where user_name=#{userName} and password = #{password}")
    User findUserByUserNameAndPassword(@Param("userName") String userName,@Param("password") String password);

    @Select("select * from user where id = #{id}")
    User findUserById(Integer id);

}
