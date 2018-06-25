package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.utils.ServiceException;

import java.util.List;

/**
 * 收货相关接口
 */
public interface UserAddressInfoService {
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
    List<UserAddressInfo> addAddress(Integer userId, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException;

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

    /**
     * 设置默认收货地址
     * @param userId
     * @param addressId
     * @throws ServiceException
     */
    void defaultAddress(Integer userId,Integer addressId) throws ServiceException;
}
