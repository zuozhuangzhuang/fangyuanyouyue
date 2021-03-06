package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.dto.*;
import com.fangyuanyouyue.goods.model.BannerIndex;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.utils.ServiceException;

import java.util.List;

public interface GoodsInfoService {
    /**
     * 根据商品ID获取商品主图
     * @param goodsId
     * @return
     * @throws ServiceException
     */
    String goodsMainImg(Integer goodsId) throws ServiceException;
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
     * 编辑商品/抢购
     * @param param
     * @throws ServiceException
     */
    void modifyGoods(GoodsParam param) throws ServiceException;
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
     * 获取商品详情中的关注信息
     * @param goodsId
     * @param userId
     * @return
     * @throws ServiceException
     */
    GoodsDto goodsInfoByToken(Integer goodsId,Integer userId) throws ServiceException;

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
    List<BannerIndexDto> getBanner(Integer type) throws ServiceException;

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

    /**
     * 获取快速查询条件
     * @return
     * @throws ServiceException
     */
    List<GoodsQuickSearchDto> quickSearch() throws ServiceException;

    /**
     * 修改商品状态
     * @param goodsId
     * @throws ServiceException
     */
    void updateGoodsStatus(Integer goodsId,Integer status) throws ServiceException;

    /**
     * 举报商品
     * @param userId
     * @param goodsId
     * @param reason
     * @throws ServiceException
     */
    void reportGoods(Integer userId,Integer goodsId,String reason) throws ServiceException;
}
