package com.fangyuanyouyue.forum.dao;

import com.fangyuanyouyue.forum.model.ForumLikes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumLikesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ForumLikes record);

    int insertSelective(ForumLikes record);

    ForumLikes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ForumLikes record);

    int updateByPrimaryKey(ForumLikes record);
}