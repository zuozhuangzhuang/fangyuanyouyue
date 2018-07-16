package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.dto.GoodsCategoryDto;
import com.fangyuanyouyue.goods.dto.GoodsDto;
import com.fangyuanyouyue.goods.dto.SearchDto;
import com.fangyuanyouyue.goods.model.BannerIndex;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.utils.ServiceException;

import java.util.List;

public interface GoodsInfoService {

    /**
     * 根据ID获取商品
     * @param id
     * @return
     */
    GoodsInfo selectByPrimaryKey(Integer id);

    /**
     * 获取商品列表
     * @param param
     * @return
     */
    List<GoodsDto> getGoodsInfoList(GoodsParam param) throws ServiceException;

    /**
     * 新增商品
     * @param param
     * @return
     * @throws ServiceException
     */
    GoodsDto addGoods(Integer userId,String nickName,GoodsParam param) throws ServiceException;

    /**
     * 批量删除商品
     * @param goodsIds
     * @throws ServiceException
     */
    void deleteGoods(Integer[] goodsIds) throws ServiceException;

    /**
     * 获取分类列表
     * @return
     * @throws ServiceException
     */
    List<GoodsCategoryDto> categoryList() throws ServiceException;

    /**
     * 商品详情
     * @param goodsId
     * @return
     * @throws ServiceException
     */
    GoodsDto goodsInfo(Integer goodsId) throws ServiceException;

    /**
     * 同类推荐
     * @param goodsId
     * @return
     * @throws ServiceException
     */
    List<GoodsDto> similarGoods(Integer goodsId,Integer pageNum, Integer pageSize) throws ServiceException;

    /**
     * 获取首页轮播图
     * @return
     * @throws ServiceException
     */
    List<BannerIndex> getBanner(Integer type) throws ServiceException;

    /**
     * 新增首页轮播图
     * @param param
     * @return
     * @throws ServiceException
     */
    BannerIndex addBanner(GoodsParam param) throws ServiceException;

    /**
     * 修改首页轮播图
     * @param param
     * @return
     * @throws ServiceException
     */
    BannerIndex updateBanner(GoodsParam param) throws ServiceException;

    /**
     * 热门搜索
     * @return
     * @throws ServiceException
     */
    List<SearchDto> hotSearch() throws ServiceException;

    /**
     * 热门分类
     * @return
     * @throws ServiceException
     */
    List<GoodsCategoryDto> hotCategary() throws ServiceException;
}
