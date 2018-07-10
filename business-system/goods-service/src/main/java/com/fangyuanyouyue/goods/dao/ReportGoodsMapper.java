package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.ReportGoods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReportGoods record);

    int insertSelective(ReportGoods record);

    ReportGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReportGoods record);

    int updateByPrimaryKey(ReportGoods record);
}