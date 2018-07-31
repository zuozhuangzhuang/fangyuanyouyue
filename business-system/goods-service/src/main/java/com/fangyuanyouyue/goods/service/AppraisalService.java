package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.utils.ServiceException;

import java.math.BigDecimal;

public interface AppraisalService {
    /**
     * 申请鉴定
     * @param userId
     * @param goodsIds
//     * @param type
     * @param title
     * @param description
     * @param price
     * @throws ServiceException
     */
    void addAppraisal(Integer userId, Integer[] goodsIds,  String title, String description, BigDecimal price,String imgUrl) throws ServiceException;
}
