package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsCorrelation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsCorrelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCorrelation record);

    int insertSelective(GoodsCorrelation record);

    GoodsCorrelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsCorrelation record);

    int updateByPrimaryKey(GoodsCorrelation record);
}