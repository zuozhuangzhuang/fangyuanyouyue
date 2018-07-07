package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKeyWithBLOBs(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    /**
     * 分页获取商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<GoodsInfo> getGoodsList(@Param("userId") Integer userId,@Param("status") Integer status,Integer pageNum, Integer pageSize);

    /**
     * 根据商品ID集合获取商品
     * @param goodsIds
     * @return
     */
    List<GoodsInfo> getGoodsByGoodsIds(@Param("goodsIds") List<Integer> goodsIds,int pageNum, int pageSize);
}