package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.a_user;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface a_userMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(a_user record);

    int insertSelective(a_user record);

    a_user selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(a_user record);

    int updateByPrimaryKey(a_user record);

    List<a_user> getList();
}