package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.service.UserAddressInfoService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.ServiceException;
import com.fangyuanyouyue.user.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userAddressInfoService")
public class UserAddressInfoServiceImpl implements UserAddressInfoService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserThirdPartyMapper userThirdPartyMapper;
    @Autowired
    private IdentityAuthApplyMapper identityAuthApplyMapper;
    @Autowired
    private UserInfoExtMapper userInfoExtMapper;
    @Autowired
    private UserAddressInfoMapper userAddressInfoMapper;
    @Autowired
    private UserVipMapper userVipMapper;
    @Autowired
    private UserExamineMapper userExamineMapper;


    @Override
    public List<UserAddressInfo> addAddress(Integer userId, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            UserAddressInfo userAddressInfo = new UserAddressInfo();
            userAddressInfo.setAddTime(DateStampUtils.getTimesteamp());
            userAddressInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            userAddressInfo.setReceiverName(receiverName);
            userAddressInfo.setReceiverPhone(receiverPhone);
            userAddressInfo.setProvince(province);
            userAddressInfo.setCity(city);
            userAddressInfo.setArea(area);
            userAddressInfo.setAddress(address);
            if(type != null){
                userAddressInfo.setType(type);
            }
            userAddressInfoMapper.insert(userAddressInfo);
            List<UserAddressInfo> userAddressInfos = userAddressInfoMapper.selectAddressByUserId(userId);
            return userAddressInfos;
        }
    }

    @Override
    public UserAddressInfo updateAddress(Integer userId, Integer addressId, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("此用户不存在！");
        }else{
            UserAddressInfo userAddressInfo = userAddressInfoMapper.selectByPrimaryKey(addressId);
            if(userAddressInfo == null){
                throw new ServiceException("收货地址有误！");
            }else{
                userAddressInfo.setReceiverName(receiverName);
                userAddressInfo.setReceiverPhone(receiverPhone);
                userAddressInfo.setProvince(province);
                userAddressInfo.setCity(city);
                userAddressInfo.setArea(area);
                userAddressInfo.setAddress(address);
                userAddressInfo.setPostCode(postCode);
                userAddressInfo.setType(type);
                userAddressInfoMapper.updateByPrimaryKey(userAddressInfo);
                return userAddressInfo;
            }
        }
    }

    @Override
    public List<UserAddressInfo> deleteAddress(Integer userId, Integer addressId) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("此用户不存在！");
        }else{
            UserAddressInfo userAddressInfo = userAddressInfoMapper.selectByPrimaryKey(addressId);
            if(userAddressInfo == null){
                throw new ServiceException("收货地址有误！");
            }else{
                userAddressInfoMapper.deleteByPrimaryKey(addressId);
                List<UserAddressInfo> userAddressInfos = userAddressInfoMapper.selectAddressByUserId(userId);
                return userAddressInfos;
            }
        }
    }

    @Override
    public void defaultAddress(Integer userId, Integer addressId) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("此用户不存在！");
        }else{
            //TODO 取消旧默认地址
            UserAddressInfo defaultAddress = userAddressInfoMapper.selectDefaultAddressByUserId(userId);
            defaultAddress.setType(Integer.valueOf(Status.OTHER.getValue()));
            //TODO 设置新默认地址
            UserAddressInfo userAddressInfo = userAddressInfoMapper.selectByPrimaryKey(addressId);
            if(userAddressInfo == null){
                throw new ServiceException("参数错误！");
            }
            userAddressInfo.setType(Integer.valueOf(Status.ISDEFAULT.getValue()));
            userAddressInfoMapper.updateByPrimaryKey(userAddressInfo);
        }
    }
}
