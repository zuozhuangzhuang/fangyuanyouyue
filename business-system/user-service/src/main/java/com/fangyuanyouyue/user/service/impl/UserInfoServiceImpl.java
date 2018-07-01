package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.dao.*;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.model.UserInfoExt;
import com.fangyuanyouyue.user.model.UserThirdParty;
import com.fangyuanyouyue.user.model.UserVip;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserInfoService;
import com.fangyuanyouyue.user.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${pic_server:errorPicServer}")
    private String PIC_SERVER;// 图片服务器

    @Value("${pic_path:errorPicPath}")
    private String PIC_PATH;// 图片存放路径

    @Override
    public UserInfo selectByPrimaryKey(Integer id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo getUserByPhone(String phone) {
        return userInfoMapper.getUserByPhone(phone);
    }

    @Override
    public UserInfo getUserByNickName(String nickName) throws ServiceException{
        UserInfo userInfo = userInfoMapper.getUserByNickName(nickName);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            return userInfo;
        }
    }

    @Override
    public UserInfo regist(UserParam param) throws ServiceException{
        //初始化用户信息
        UserInfo user = new UserInfo();
        //手机号注册必定是APP端
        // 保存头像
        saveHeadImg(param.getHeadImg(),user);
        //保存背景图片
        saveBgImg(param.getBgImg(),user);

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
        userInfoExt.setStatus(2);//实名登记状态 1已实名 2未实名
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
        user.setStatus(1);//状态 1正常 2冻结
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
        userInfoExt.setStatus(2);//实名登记状态 1已实名 2未实名
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
    public void resetPwd(String phone, String newPwd) throws ServiceException{
        UserInfo userInfo = userInfoMapper.getUserByPhone(phone);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            if(MD5Util.getMD5String(newPwd).equals(userInfo.getLoginPwd())){
                throw new ServiceException("不能和旧密码相同！");
            }else{
                userInfo.setLoginPwd(MD5Util.getMD5String(newPwd));
                userInfo.setUpdateTime(DateStampUtils.getTimesteamp());
                userInfoMapper.updateByPrimaryKey(userInfo);
            }
        }
    }

    @Override
    public void updatePwd(Integer userId, String newPwd) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            if(MD5Util.getMD5String(newPwd).equals(userInfo.getLoginPwd())){
                throw new ServiceException("不能和旧密码相同！");
            }else{
                userInfo.setLoginPwd(MD5Util.getMD5String(newPwd));
                userInfo.setUpdateTime(DateStampUtils.getTimesteamp());
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
            // 保存头像
            saveHeadImg(param.getHeadImg(),userInfo);
            //保存背景图片
            saveBgImg(param.getBgImg(),userInfo);
            if(param.getGender() != null){
                userInfo.setGender(param.getGender());
            }
            if(StringUtils.isNotEmpty(param.getSignature())){
                userInfo.setSignature(param.getSignature());
            }
            if(StringUtils.isNotEmpty(param.getContact())){
                userInfo.setContact(param.getContact());
            }
            if(StringUtils.isNotEmpty(param.getUserAddress())){
                userInfo.setAddress(param.getUserAddress());
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


    private void saveHeadImg(MultipartFile headImg,UserInfo userInfo) throws ServiceException {
        String date = DateUtil.getCurrentDate("/yyyy/MM/dd/");
        FileUtil util = new FileUtil();
        String fileName;
        if(headImg != null){
            try {
                fileName = util.getFileName(headImg, "HEADIMG");
                String name = fileName.toLowerCase();
                if (name.endsWith("jpeg") || name.endsWith("png")
                        || name.endsWith("jpg")) {
                    util.saveFile(headImg, PIC_PATH + date, fileName);
                    userInfo.setHeadImgUrl(PIC_SERVER + date+fileName);
                } else {
                    throw new ServiceException("请上传JPEG/PNG/JPG格式化图片！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存头像图片出错！");
            }
        }
    }

    private void saveBgImg(MultipartFile bgImg, UserInfo userInfo) throws ServiceException {
        String date = DateUtil.getCurrentDate("/yyyy/MM/dd/");
        FileUtil util = new FileUtil();
        String fileName;
        if(bgImg != null){
            try {
                fileName = util.getFileName(bgImg, "BGIMG");
                String name = fileName.toLowerCase();
                if (name.endsWith("jpeg") || name.endsWith("png")
                        || name.endsWith("jpg")) {
                    util.saveFile(bgImg, PIC_PATH + date, fileName);
                    userInfo.setBgImgUrl(PIC_SERVER + date+fileName);
                } else {
                    throw new ServiceException("请上传JPEG/PNG/JPG格式化图片！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存背景图片出错！");
            }
        }
    }

    @Override
    public UserInfo updatePhone(Integer userId, String phone) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("用户不存在！");
        }else{
            userInfo.setPhone(phone);
            userInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            userInfoMapper.updateByPrimaryKey(userInfo);
            return userInfo;
        }
    }

    @Override
    public UserInfo accountMerge(Integer userId, String phone) throws ServiceException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo == null){
            throw new ServiceException("第三方用户不存在！");
        }else{
            UserInfo userInfoByPhone = userInfoMapper.getUserByPhone(phone);
            if(userInfoByPhone == null){
                throw new ServiceException("手机号用户不存在！");
            }else{
                //TODO 合并余额、粉丝、关注、收藏、商品、收货地址、好友、会员时间、
                //第三方登录账号在未绑定手机号时拥有功能：

                userInfo.setUpdateTime(DateStampUtils.getTimesteamp());
                userInfoMapper.updateByPrimaryKey(userInfo);
                return userInfo;
            }
        }
    }
}
