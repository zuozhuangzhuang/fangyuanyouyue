package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.utils.ServiceException;

/**
 * 实名认证相关接口
 */
public interface UserInfoExtService {
    /**
     * 实名认证
     * @param userId
     * @param name
     * @param identity
     * @param identityImgCover
     * @param identityImgBack
     * @throws ServiceException
     */
    void certification(Integer userId, String name, String identity, String identityImgCover, String identityImgBack) throws ServiceException;
}
