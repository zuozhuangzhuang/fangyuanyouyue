package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.model.UserInfoExt;
import com.fangyuanyouyue.user.model.UserThirdParty;
import com.fangyuanyouyue.user.model.UserVip;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserInfoService;
import com.fangyuanyouyue.user.utils.DateStampUtils;
import com.fangyuanyouyue.user.utils.MD5Util;
import com.fangyuanyouyue.user.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

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
    public UserInfo selectByPrimaryKey(Integer id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo getUserByPhone(String phone) {
        return userInfoMapper.getUserByPhone(phone);
    }

    @Override
    public UserInfo getUserByNickName(String nickName) {
        UserInfo userInfo = userInfoMapper.getUserByNickName(nickName);
        return userInfo;
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
        user.setRegType(1);//注册来源 1APP 2微信小程序
        user.setRegPlatform(param.getRegPlatform());
        user.setAddTime(DateStampUtils.getTimesteamp());
        user.setUpdateTime(DateStampUtils.getTimesteamp());
        user.setPhone(param.getPhone());
        user.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
        user.setNickName(param.getPhone());
        user.setStatus(1);//状态 1正常 2冻结
        user.setGender(param.getGender());
        userInfoMapper.insert(user);
        //用户扩展信息表
        UserInfoExt userInfoExt = new UserInfoExt();
        userInfoExt.setUserId(user.getId());
        userInfoExt.setStatus(2);
        userInfoExt.setAddTime(DateStampUtils.getTimesteamp());
        userInfoExt.setUpdateTime(DateStampUtils.getTimesteamp());
        userInfoExtMapper.insert(userInfoExt);
        //用户会员系统
        UserVip userVip = new UserVip();
        userVip.setUserId(user.getId());
        userVip.setStatus(2);//会员状态1已开通 2未开通
        userVip.setAddTime(DateStampUtils.getTimesteamp());
        userVip.setUpdateTime(DateStampUtils.getTimesteamp());
        userVipMapper.insert(userVip);
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
        //初始化用户信息
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
        //初始化用户第三方登录信息
        UserThirdParty userThirdParty = new UserThirdParty();
        userThirdParty.setUserId(user.getId());
        userThirdParty.setNickName(param.getThirdNickName());
        userThirdParty.setHeadImgUrl(param.getThirdHeadImgUrl());
        userThirdParty.setUnionId(param.getUnionId());
        userThirdParty.setType(param.getType());
        userThirdParty.setAddTime(DateStampUtils.getTimesteamp());
        userThirdParty.setUpdateTime(DateStampUtils.getTimesteamp());
        userThirdPartyMapper.insert(userThirdParty);
        //用户扩展信息表
        UserInfoExt userInfoExt = new UserInfoExt();
        userInfoExt.setUserId(user.getId());
        userInfoExt.setStatus(2);
        userInfoExt.setAddTime(DateStampUtils.getTimesteamp());
        userInfoExt.setUpdateTime(DateStampUtils.getTimesteamp());
        userInfoExtMapper.insert(userInfoExt);
        //用户会员系统
        UserVip userVip = new UserVip();
        userVip.setUserId(user.getId());
        userVip.setStatus(2);//会员状态1已开通 2未开通
        userVip.setAddTime(DateStampUtils.getTimesteamp());
        userVip.setUpdateTime(DateStampUtils.getTimesteamp());
        userVipMapper.insert(userVip);
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
    public UserInfo modify(UserParam param) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(param.getUserId());
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            //用户信息
            if(StringUtils.isNotEmpty(param.getPhone())){
                userInfo.setPhone(param.getPhone());
            }
            if(StringUtils.isNotEmpty(param.getEmail())){
                userInfo.setEmail(param.getEmail());
            }
            if(StringUtils.isNotEmpty(param.getNickName())){
                userInfo.setNickName(param.getNickName());
            }
            if(StringUtils.isNotEmpty(param.getHeadImgUrl())){
                userInfo.setHeadImgUrl(param.getHeadImgUrl());
            }
            if(StringUtils.isNotEmpty(param.getBgImgUrl())){
                userInfo.setBgImgUrl(param.getBgImgUrl());
            }
            if(param.getGender() != null){
                userInfo.setGender(param.getGender());
            }
            if(StringUtils.isNotEmpty(param.getSignature())){
                userInfo.setSignature(param.getSignature());
            }
            if(StringUtils.isNotEmpty(param.getContact())){
                userInfo.setContact(param.getContact());
            }
            userInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            userInfoMapper.updateByPrimaryKey(userInfo);
            //用户扩展信息表
            UserInfoExt userInfoExt = userInfoExtMapper.selectByUserId(param.getUserId());
            if(StringUtils.isNotEmpty(param.getIdentity())){
                userInfoExt.setIdentity(param.getIdentity());
            }
            if(StringUtils.isNotEmpty(param.getName())){
                userInfoExt.setName(param.getName());
            }
            if(StringUtils.isNotEmpty(param.getPayPwd())){
                userInfoExt.setPayPwd(MD5Util.getMD5String(param.getPayPwd()));
            }
            userInfoExt.setUpdateTime(DateStampUtils.getTimesteamp());
            userInfoExtMapper.updateByPrimaryKey(userInfoExt);
            return userInfo;
        }
    }




}
