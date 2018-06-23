package com.fangyuanyouyue.user.dao;

import com.fangyuanyouyue.user.model.UserExamine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserExamineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserExamine record);

    int insertSelective(UserExamine record);

    UserExamine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserExamine record);

    int updateByPrimaryKey(UserExamine record);

    UserExamine getUserExamineByUserId(Integer userId);
}