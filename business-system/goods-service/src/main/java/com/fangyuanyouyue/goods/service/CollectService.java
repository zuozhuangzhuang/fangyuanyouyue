package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.dto.GoodsDto;
import com.fangyuanyouyue.goods.utils.ServiceException;

import java.util.List;

public interface CollectService {
    /**
     * 收藏/关注或取消
     * @param userId
     * @param collectId
     * @param collectType
     * @param type
     * @param status
     * @throws ServiceException
     */
    void collectGoods(Integer userId,Integer collectId,Integer collectType,Integer type,Integer status) throws ServiceException;

    /**
     * 我的收藏/关注列表 (商品、抢购)
     * @param userId
     * @param collectType
     * @param type
     * @return
     * @throws ServiceException
     */
    List<GoodsDto> collectList(Integer userId, Integer collectType, Integer type) throws ServiceException;
}
