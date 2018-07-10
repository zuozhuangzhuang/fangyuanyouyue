package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsAppraisalDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsAppraisalDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsAppraisalDetail record);

    int insertSelective(GoodsAppraisalDetail record);

    GoodsAppraisalDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsAppraisalDetail record);

    int updateByPrimaryKey(GoodsAppraisalDetail record);
}