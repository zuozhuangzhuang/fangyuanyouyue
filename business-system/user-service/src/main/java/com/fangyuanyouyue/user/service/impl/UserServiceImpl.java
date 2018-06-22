package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.IdentityAuthApply;
import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.model.UserInfoExt;
import com.fangyuanyouyue.user.model.UserThirdParty;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.MD5Util;
import com.fangyuanyouyue.user.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

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

    @Override
    public UserInfo selectByPrimaryKey(Integer id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo getUserByPhone(String phone) {
        return userInfoMapper.getUserByPhone(phone);
    }

//    @Override
//    public UserInfo getUserByToken(String token) {
//        return userInfoMapper.getUserByToken(token);
//    }

    @Override
    public void updateByPrimaryKey(UserParam param) {
        //根据手机号获取用户，修改密码
        UserInfo user = userInfoMapper.getUserByPhone(param.getPhone());
        if(user!=null){
            user.setLoginPwd(MD5Util.getMD5String(param.getNewPwd()));
            userInfoMapper.updateByPrimaryKey(user);
        }
    }

    @Override
    public UserInfo regist(UserParam param) throws ServiceException{
        //初始化用户信息
        UserInfo user = new UserInfo();
        //手机号注册必定是APP端
        user.setRegType(1);
        user.setRegPlatform(param.getRegPlatform());
        user.setAddTime(DateStampUtils.getTimesteamp());
        user.setUpdateTime(DateStampUtils.getTimesteamp());
        user.setPhone(param.getPhone());
        user.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
        user.setNickName(param.getPhone());
        user.setStatus(1);
        if(param.getGender() != null){//性别默认为 2不确定
            user.setGender(param.getGender());
        }
        userInfoMapper.insert(user);
        //TODO 注册通讯账户
        //TODO 调用钱包系统初始化接口
        //初始化用户钱包
        return user;
    }

    @Override
    public UserInfo login(String phone,String loginPwd,Integer lastLoginPlatform) throws ServiceException{
        UserInfo user = userInfoMapper.getUserByPhone(phone);
        //账号或密码错误处理
        if(user == null){
            throw new ServiceException("账号不正确！");
        }else{
            if(!user.getLoginPwd().equals(loginPwd)){
                //密码错误次数
                if(user.getPwdErrCount() == null || user.getPwdErrCount() == 0){
                    user.setPwdErrCount(1);
                }else{
                    user.setPwdErrCount(user.getPwdErrCount()+1);
                }
                userInfoMapper.updateByPrimaryKey(user);
                //TODO 根据密码错误次数进行操作
                throw new ServiceException("密码不正确！");
            }else{
                user.setPwdErrCount(0);
                user.setLastLoginTime(DateStampUtils.getTimesteamp());
                user.setLastLoginPlatform(lastLoginPlatform);
                userInfoMapper.updateByPrimaryKey(user);
                //TODO 注册通讯账户
                //TODO 获取用户的相关信息：商品列表、钱包系统、好友列表
                return user;
            }
        }
    }

    @Override
    public UserInfo thirdRegister(UserParam param)  throws ServiceException{
        //TODO 初始化用户信息
        UserInfo user = new UserInfo();
        user.setNickName(param.getThirdNickName());
        user.setHeadImgUrl(param.getThirdHeadImgUrl());
        user.setRegType(param.getRegType());
        user.setRegPlatform(param.getRegPlatform());
        user.setAddTime(DateStampUtils.getTimesteamp());
        user.setUpdateTime(DateStampUtils.getTimesteamp());
        user.setStatus(1);
        if(param.getGender() != null){
            user.setGender(param.getGender());
        }
        userInfoMapper.insert(user);
        //TODO 初始化用户第三方登录信息
        UserThirdParty userThirdParty = new UserThirdParty();
        userThirdParty.setUserId(user.getId());
        userThirdParty.setNickName(param.getThirdNickName());
        userThirdParty.setHeadImgUrl(param.getThirdHeadImgUrl());
        userThirdParty.setUnionId(param.getUnionId());
        userThirdParty.setType(param.getType());
        userThirdParty.setAddTime(DateStampUtils.getTimesteamp());
        userThirdParty.setUpdateTime(DateStampUtils.getTimesteamp());
        userThirdPartyMapper.insert(userThirdParty);
        //TODO 注册通讯账户
        //TODO 调用钱包系统初始化接口
        //初始化用户钱包
        return user;
    }

    @Override
    public UserInfo thirdLogin(String unionId,Integer type,Integer lastLoginPlatform) throws ServiceException {
        //根据第三方唯一ID和类型获取第三方登录信息
        UserThirdParty userThirdParty = userThirdPartyMapper.getUserByThirdNoType(unionId,type);
        if(userThirdParty == null){
            throw new ServiceException("用户不存在！");
        }else{
            //TODO 记录用户登录时间，登录平台，最后一次登录
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userThirdParty.getUserId());
            userInfo.setLastLoginTime(DateStampUtils.getTimesteamp());
            userInfo.setLastLoginPlatform(lastLoginPlatform);
            userInfoMapper.updateByPrimaryKey(userInfo);
            return userInfo;
        }
    }

    @Override
    public UserInfo thirdBind(Integer userId,String unionId,Integer type) throws ServiceException {
        //根据用户ID获取用户，生成新的三方登陆信息
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            UserThirdParty userThirdParty = userThirdPartyMapper.getUserByThirdNoType(unionId,type);
            if(userThirdParty != null){
                if(userThirdParty.getUserId() == userId){
                    throw new ServiceException("请勿重复绑定！");
                }else{
                    throw new ServiceException("已绑定其他用户！");
                }
            }else{
                userThirdParty.setUserId(userInfo.getId());
    //            userThirdParty.setNickName(userInfo.getNickName());
    //            userThirdParty.setHeadImgUrl(userInfo.getHeadImgUrl());
                userThirdParty.setType(type);
                userThirdParty.setUnionId(unionId);
                userThirdParty.setAddTime(DateStampUtils.getTimesteamp());
                userThirdParty.setUpdateTime(DateStampUtils.getTimesteamp());
                userThirdPartyMapper.insert(userThirdParty);
                return userInfo;
            }
        }
    }

    @Override
    public void certification(Integer userId, String name, String identity, String identityImgCover, String identityImgBack) throws ServiceException {
        IdentityAuthApply identityAuthApply = identityAuthApplyMapper.selectByUserId(userId);
        if(identityAuthApply != null){
            if(identityAuthApply.getStatus() == 1){
                throw new ServiceException("您已提交过实名认证，请耐心等待！");
            }else if(identityAuthApply.getStatus() == 2){
                throw new ServiceException("您的实名认证已通过，请勿重复提交！");
            }
        }else{
            //用户扩展信息表
            UserInfoExt userInfoExt = userInfoExtMapper.selectByUserId(userId);
            userInfoExt.setAddTime(DateStampUtils.getTimesteamp());
            userInfoExt.setUpdateTime(DateStampUtils.getTimesteamp());
            userInfoExt.setIdentity(identity);
            userInfoExt.setName(name);
            userInfoExt.setUserId(userId);
            userInfoExt.setStatus(2);
            userInfoExtMapper.insert(userInfoExt);

            //实名认证申请表
            identityAuthApply.setAddTime(DateStampUtils.getTimesteamp());
            identityAuthApply.setUpdateTime(DateStampUtils.getTimesteamp());
            identityAuthApply.setIdentity(identity);
            identityAuthApply.setName(name);
            identityAuthApply.setStatus(1);
            if(identityImgCover != null && !identityImgCover.equals("")){
                identityAuthApply.setIdentityImgCover(identityImgCover);
            }
            if(identityImgBack != null && !identityImgBack.equals("")){
                identityAuthApply.setIdentityImgBack(identityImgBack);
            }
            identityAuthApplyMapper.insert(identityAuthApply);
        }
    }

    @Override
    public void resetPwd(Integer userId, String newPwd) throws ServiceException{
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            if(MD5Util.getMD5String(newPwd).equals(userInfo.getLoginPwd())){
                throw new ServiceException("不能和旧密码相同！");
            }else{
                userInfo.setLoginPwd(MD5Util.getMD5String(newPwd));
                userInfoMapper.updateByPrimaryKey(userInfo);
            }
        }
    }

    @Override
    public List<UserAddressInfo> addAddress(Integer userId, String receiverName, String receiverPhone, String province, String city, String area, String address, String postCode, Integer type) throws ServiceException{
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
}
