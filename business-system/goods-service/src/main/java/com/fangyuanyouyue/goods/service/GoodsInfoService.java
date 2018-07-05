package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.dto.GoodsDto;
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
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<GoodsDto> getGoodsInfoList(int pageNum, int pageSize) throws ServiceException;

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
}
