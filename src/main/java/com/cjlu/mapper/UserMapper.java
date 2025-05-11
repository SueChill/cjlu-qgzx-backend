package com.cjlu.mapper;

import com.cjlu.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    User findByNumberAndPassword(@Param("number") String number,
                                 @Param("password") String password);

    User findByIdWithDetail(Integer userId);

    //学生用户信息注册后插入以及查重操作
    @Insert("INSERT INTO User (name, college, number, password, phone, role) " +
            "VALUES (#{name}, #{college}, #{number}, #{password}, #{phone}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    @Select("SELECT COUNT(*) FROM User WHERE number = #{number}")
    boolean existsByNumber(String number);

    @Select("SELECT COUNT(*) FROM User WHERE phone = #{phone}")
    boolean existsByPhone(String phone);
}