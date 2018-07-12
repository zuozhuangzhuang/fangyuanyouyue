package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.UserInfoExt;

public interface UserInfoExtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoExt record);

    int insertSelective(UserInfoExt record);

    UserInfoExt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoExt record);

    int updateByPrimaryKey(UserInfoExt record);

    UserInfoExt selectByUserId(int userId);
}