package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.UserExamine;
import com.fangyuanyouyue.user.utils.ServiceException;

/**
 * 用户审核相关接口
 */
public interface UserExamineService {
    /**
     * 根据用户ID获取昵称申请记录
     * @param token
     * @return
     * @throws ServiceException
     */
    UserExamine getUserExamineByUserId(String token) throws ServiceException;
}
