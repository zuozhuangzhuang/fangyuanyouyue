package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.utils.ServiceException;

/**
 * 用户相关接口
 */
public interface UserInfoService {


    /**
     * 根据ID获取用户
     * @param id
     * @return
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * 根据手机获取用户
     * @param phone
     * @return
     */
    UserInfo getUserByPhone(String phone);

    /**
     * 根据昵称获取用户
     * @param nickName
     * @return
     */
    UserInfo getUserByNickName(String nickName);


    /**
     * 修改用户信息
     * @param param
     * @return
     */
    void updateByPrimaryKey(UserParam param);

    /**
     * 用户手机注册
     * @param param
     * @return
     */
    UserInfo regist(UserParam param) throws ServiceException;

    /**
     * 用户手机号登录
     * @param phone
     * @param logingPwd
     * @param lastLoginPlatform
     * @return
     */
    UserInfo login(String phone,String logingPwd,Integer lastLoginPlatform) throws ServiceException;

    /**
     * 三方注册
     * @param param
     * @return
     */
    UserInfo thirdRegister(UserParam param) throws ServiceException;

    /**
     * 三方登陆
     * @param unionId
     * @param type
     * @return
     * @throws ServiceException
     */
    UserInfo thirdLogin(String unionId,Integer type,Integer lastLoginPlatform) throws ServiceException;

    /**
     * 三方绑定
     * @param userId
     * @param unionId
     * @param type
     * @return
     * @throws ServiceException
     */
    UserInfo thirdBind(Integer userId,String unionId,Integer type) throws ServiceException;



    /**
     * 完善资料
     * @param param
     * @return
     * @throws ServiceException
     */
    UserInfo modify(UserParam param) throws ServiceException;

    /**
     * 找回密码
     * @param phone
     * @param newPwd
     * @throws ServiceException
     */
    void resetPwd(String phone,String newPwd) throws ServiceException;

    /**
     * 修改密码
     * @param userId
     * @param newPwd
     * @throws ServiceException
     */
    void updatePwd(Integer userId,String newPwd) throws ServiceException;

    /**
     * 修改绑定手机
     * @param userId
     * @param phone
     * @throws ServiceException
     */
    UserInfo updatePhone(Integer userId,String phone) throws ServiceException;

}
