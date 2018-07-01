package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.CartDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CartDetail record);

    int insertSelective(CartDetail record);

    CartDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CartDetail record);

    int updateByPrimaryKey(CartDetail record);
}