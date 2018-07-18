package com.fangyuanyouyue.forum.dao;

import com.fangyuanyouyue.forum.model.ForumColumn;

public interface ForumColumnMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForumColumn record);

    int insertSelective(ForumColumn record);

    ForumColumn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForumColumn record);

    int updateByPrimaryKey(ForumColumn record);
}