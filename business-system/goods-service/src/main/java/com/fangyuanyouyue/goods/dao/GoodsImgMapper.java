package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsImgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsImg record);

    int insertSelective(GoodsImg record);

    GoodsImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsImg record);

    int updateByPrimaryKey(GoodsImg record);

    List<GoodsImg> getImgsByGoodsId(Integer goodsId);
}