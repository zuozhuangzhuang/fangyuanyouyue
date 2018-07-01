package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsComment record);

    int insertSelective(GoodsComment record);

    GoodsComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsComment record);

    int updateByPrimaryKey(GoodsComment record);
}