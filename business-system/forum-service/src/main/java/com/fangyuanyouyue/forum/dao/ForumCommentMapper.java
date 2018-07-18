package com.fangyuanyouyue.forum.dao;

import com.fangyuanyouyue.forum.model.ForumComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForumComment record);

    int insertSelective(ForumComment record);

    ForumComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForumComment record);

    int updateByPrimaryKey(ForumComment record);

    int countAll();
    
}