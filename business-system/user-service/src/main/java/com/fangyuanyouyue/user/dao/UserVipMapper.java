package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.UserVip;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserVip record);

    int insertSelective(UserVip record);

    UserVip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserVip record);

    int updateByPrimaryKey(UserVip record);

    UserVip getUserVipByUserId(Integer userId);
}