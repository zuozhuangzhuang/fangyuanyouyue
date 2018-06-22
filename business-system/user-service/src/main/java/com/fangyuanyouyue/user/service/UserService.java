package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.utils.ServiceException;

import java.util.List;

public interface UserService {


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
     * 根据token获取用户
     * @param token
     * @return
     */
//    UserInfo getUserByToken(String token);

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
     * 实名认证
     * @param userId
     * @param name
     * @param identity
     * @param identityImgCover
     * @param identityImgBack
     * @throws ServiceException
     */
    void certification(Integer userId, String name, String identity, String identityImgCover, String identityImgBack) throws ServiceException;

    /**
     * 重置密码
     * @param userId
     * @param newPwd
     * @throws ServiceException
     */
    void resetPwd(Integer userId,String newPwd) throws ServiceException;

    /**
     * 添加收货地址
     * @param userId
     * @param receiverName
     * @param receiverPhone
     * @param province
     * @param city
     * @param area
     * @param address
     * @param postCode
     * @param type
     * @return
     * @throws ServiceException
     */
    List<UserAddressInfo> addAddress(Integer userId,String receiverName,String receiverPhone,String province,String city,String area,String address,String postCode,Integer type) throws ServiceException;

    /**
     * 修改收货地址
     * @param userId
     * @param addressId
     * @param receiverName
     * @param receiverPhone
     * @param province
     * @param city
     * @param area
     * @param address
     * @param postCode
     * @param type
     * @return
     */
    UserAddressInfo updateAddress(Integer userId,Integer addressId,String receiverName,String receiverPhone,String province,String city,String area,String address,String postCode,Integer type) throws ServiceException;

    /**
     * 删除收货地址
     * @param userId
     * @param addressId
     * @return
     * @throws ServiceException
     */
    List<UserAddressInfo> deleteAddress(Integer userId,Integer addressId) throws ServiceException;
}
