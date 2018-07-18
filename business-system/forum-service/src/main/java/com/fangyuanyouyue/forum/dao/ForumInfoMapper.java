package com.fangyuanyouyue.forum.dao;

import com.fangyuanyouyue.forum.model.ForumInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumInfoMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(ForumInfo record);

    int insertSelective(ForumInfo record);

    ForumInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForumInfo record);

    int updateByPrimaryKeyWithBLOBs(ForumInfo record);

    int updateByPrimaryKey(ForumInfo record);
}