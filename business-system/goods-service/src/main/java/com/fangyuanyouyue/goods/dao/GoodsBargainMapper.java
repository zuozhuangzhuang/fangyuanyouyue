package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsBargain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsBargainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsBargain record);

    int insertSelective(GoodsBargain record);

    GoodsBargain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsBargain record);

    int updateByPrimaryKey(GoodsBargain record);
}