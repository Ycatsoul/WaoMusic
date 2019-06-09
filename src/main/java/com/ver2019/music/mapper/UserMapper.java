package com.ver2019.music.mapper;

import com.ver2019.music.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from User where userid=#{id}")
    User findById(String id);

    @Select("select * from User where userName=#{userName} and delflag=0")
    List<User> findAllByUserName(String userName);

    @Select("select * from User where userName=#{userName} and delflag=0 limit 1")
    User findOneByUserName(String userName);

    @Select("select * from User where userName=#{userName} and password=#{password} and delflag=0 limit 1")
    User checkIsExit(@Param("userName")String userName,@Param("password")String password);

    /*@SelectKey(keyProperty = "user.id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "user.id", useGeneratedKeys = true)*/
    @Insert("insert into User(userid,userName,password,userType,sex,email,mobile,createTime,updateTime)"
            +"value(#{user.id},#{user.userName},#{user.password},#{user.userType},#{user.sex},#{user.email},#{user.mobile},now(),now())")
    Integer save(@Param("user")User user);

    /*@Delete("delete form User where id=#{id}")
    String deleteById(String id);*/

    @Update("update User set delflag=1,updateTime=now() where userid=#{id}")
    Boolean deleteById(@Param("id")long id);
}
