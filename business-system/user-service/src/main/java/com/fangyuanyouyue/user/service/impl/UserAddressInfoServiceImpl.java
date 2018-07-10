package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.dto.UserAddressDto;
import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.service.UserAddressInfoService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.ServiceException;
import com.fangyuanyouyue.user.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public List<UserAddressDto> addAddress(String token, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException {
        Integer userId = (Integer)redisTemplate.opsForValue().get(token);
        redisTemplate.expire(token,7, TimeUnit.DAYS);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            UserAddressInfo userAddressInfo = new UserAddressInfo();
            userAddressInfo.setUserId(userId);
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
            List<UserAddressDto> userAddressDtos = new ArrayList<>();
            for(UserAddressInfo userAddress:userAddressInfos){
                userAddressDtos.add(new UserAddressDto(userAddressInfo));
            }
            return userAddressDtos;
        }
    }

    @Override
    public UserAddressDto updateAddress(String token, Integer addressId, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException {
        Integer userId = (Integer)redisTemplate.opsForValue().get(token);
        redisTemplate.expire(token,7,TimeUnit.DAYS);
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
                userAddressInfo.setUpdateTime(DateStampUtils.getTimesteamp());
                userAddressInfoMapper.updateByPrimaryKey(userAddressInfo);
                return new UserAddressDto(userAddressInfo);
            }
        }
    }

    @Override
    public List<UserAddressDto> deleteAddress(String token, Integer addressId) throws ServiceException {
        Integer userId = (Integer)redisTemplate.opsForValue().get(token);
        redisTemplate.expire(token,7,TimeUnit.DAYS);
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
                List<UserAddressDto> userAddressDtos = new ArrayList<>();
                for(UserAddressInfo userAddress:userAddressInfos){
                    userAddressDtos.add(new UserAddressDto(userAddressInfo));
                }
                return userAddressDtos;
            }
        }
    }

    @Override
    public void defaultAddress(String token, Integer addressId) throws ServiceException {
        Integer userId = (Integer)redisTemplate.opsForValue().get(token);
        redisTemplate.expire(token,7,TimeUnit.DAYS);
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("此用户不存在！");
        }else{
            //TODO 取消旧默认地址
            UserAddressInfo defaultAddress = userAddressInfoMapper.selectDefaultAddressByUserId(userId);
            defaultAddress.setType(Integer.valueOf(Status.OTHER.getValue()));
            defaultAddress.setUpdateTime(DateStampUtils.getTimesteamp());
            userAddressInfoMapper.updateByPrimaryKey(defaultAddress);
            //TODO 设置新默认地址
            UserAddressInfo userAddressInfo = userAddressInfoMapper.selectByPrimaryKey(addressId);
            if(userAddressInfo == null){
                throw new ServiceException("参数错误！");
            }
            userAddressInfo.setType(Integer.valueOf(Status.ISDEFAULT.getValue()));
            userAddressInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            userAddressInfoMapper.updateByPrimaryKey(userAddressInfo);
        }
    }

}
