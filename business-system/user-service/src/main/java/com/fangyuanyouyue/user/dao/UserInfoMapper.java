package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo getUserByPhone(String phone);

    UserInfo getUserByPhonePwd(@Param("phone") String phone, @Param("loginPwd") String loginPwd);
}