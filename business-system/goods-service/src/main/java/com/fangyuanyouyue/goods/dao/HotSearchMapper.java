package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.HotSearch;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotSearchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotSearch record);

    int insertSelective(HotSearch record);

    HotSearch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotSearch record);

    int updateByPrimaryKey(HotSearch record);

    HotSearch selectByName(String name);
}