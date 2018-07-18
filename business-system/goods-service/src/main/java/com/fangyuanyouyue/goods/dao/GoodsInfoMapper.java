package com.fangyuanyouyue.goods.dao;

import com.fangyuanyouyue.goods.model.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface GoodsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsInfo record);

    int insertSelective(GoodsInfo record);

    GoodsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsInfo record);

    int updateByPrimaryKeyWithBLOBs(GoodsInfo record);

    int updateByPrimaryKey(GoodsInfo record);

    /**
     *分页获取商品/抢购列表
     * @param userId
     * @param status
     * @param search
     * @param priceMin
     * @param priceMax
     * @param synthesize
     * @param quality
     * @param start
     * @param limit
     * @param type
     * @return
     */

    List<GoodsInfo> getGoodsList(@Param("userId") Integer userId, @Param("status") Integer status, @Param("search") String search,
                                 @Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax,
                                 @Param("synthesize")Integer synthesize, @Param("quality")Integer quality,
                                 @Param("start") Integer start, @Param("limit") Integer limit, @Param("type")Integer type,
                                 @Param("goodsCategoryIds")Integer[] goodsCategoryIds);

    /**
     * 根据商品ID集合获取商品
     * @param goodsIds
     * @return
     */
    List<GoodsInfo> getGoodsByGoodsIds(@Param("goodsIds") List<Integer> goodsIds,int pageNum, int pageSize);

    /**
     * 根据当前时间获取需要降价的抢购列表
     * @param nowTime
     * @return
     */
    List<GoodsInfo> selectGoodsByIntervalTime(@Param("nowTime") Long nowTime);

    /**
     * 获取我收藏/关注的商品、抢购列表
     * @param userId
     * @param collectType
     * @param type
     * @return
     */
    List<GoodsInfo> selectMyCollectGoods(@Param("userId")Integer userId,@Param("collectType")Integer collectType,@Param("type")Integer type);
}