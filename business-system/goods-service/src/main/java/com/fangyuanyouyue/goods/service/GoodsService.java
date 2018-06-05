package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.model.Goods;

import java.util.List;

public interface GoodsService {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> getGoodsList(int pageNum, int pageSize);
}
