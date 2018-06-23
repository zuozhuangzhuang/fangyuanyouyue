package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseClientResult;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.model.UserAddressInfo;
import com.fangyuanyouyue.user.model.UserExamine;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.*;
import com.fangyuanyouyue.user.utils.MD5Util;
import com.fangyuanyouyue.user.utils.ServiceException;
import com.fangyuanyouyue.user.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
@Api(description = "用户系统Controller")
@RefreshScope
public class UserController extends BaseController {
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoExtService userInfoExtService;
    @Autowired
    private UserExamineService userExamineService;
    @Autowired
    private UserAddressInfoService userAddressInfoService;
    @Autowired
    private SchedualGoodsService schedualGoodsService;//调用其他service时用

    @ApiOperation(value = "注册", notes = "注册",position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickName", value = "昵称",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headImgUrl", value = "头像地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别，0女 1男 2不确定", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "regPlatform", value = "注册平台 1安卓 2IOS 3小程序", required = true, dataType = "Integer", paramType = "query")

    })
    @PostMapping(value = "/regist")
    @ResponseBody
    public String regist(UserParam param) throws IOException {
        try {
            log.info("----》注册《----");
            log.info("参数：" + param.toString());
            if(param.getRegPlatform() == null){
                return toError("注册平台不能为空！");
            }
            if (StringUtils.isEmpty(param.getPhone())) {
                return toError("手机号码不能为空！");
            }
            if (StringUtils.isEmpty(param.getLoginPwd())) {
                return toError("登录密码不能为空！");
            }
            if (StringUtils.isEmpty(param.getNickName())) {
                return toError("用户昵称不能为空！");
            }
            UserInfo userInfo = userInfoService.getUserByPhone(param.getPhone());
            if (userInfo != null) {
                return toError("手机号码已被注册！");
            }
            //TODO 注册
            userInfo = userInfoService.regist(param);
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "注册成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户登录",position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lastLoginPlatform", value = "登录平台 1安卓 2IOS 3小程序", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(UserParam param) throws IOException {
        try {
            log.info("----》用户登录《----");
            log.info("参数：" + param.toString());
            if (StringUtils.isEmpty(param.getPhone())) {
                return toError("手机号码不能为空！");
            }
            if (StringUtils.isEmpty(param.getLoginPwd())) {
                return toError("密码不能为空！");
            }
            if(param.getLastLoginPlatform() == null){
                return toError("登录平台不能为空！");
            }
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 用户登录
            UserInfo userInfo = userInfoService.login(param.getPhone(),param.getLoginPwd(),param.getLastLoginPlatform());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "登陆成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "三方注册", notes = "三方注册",position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thirdNickName", value = "第三方账号昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdHeadImgUrl", value = "第三方账号头像地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别，0女 1男 2不确定", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "第三方唯一ID",required = true,  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "regType", value = "注册来源 1app 2微信小程序",required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "regPlatform", value = "注册平台 1安卓 2IOS 3小程序",required = true,  dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博",required = true,  dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/thirdRegister")
    @ResponseBody
    public String thirdRegister(UserParam param) throws IOException {
        try {
            log.info("----》三方注册《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getUnionId())){
                return toError("第三方唯一ID不能为空！");
            }
            if(StringUtils.isEmpty(param.getThirdNickName())){
                return toError("第三方账号昵称不能为空！");
            }
            if(param.getRegType() == null){
                return toError("注册来源不能为空！");
            }
            if(param.getRegPlatform() == null){
                return toError("注册平台不能为空！");
            }
            if(param.getType() == null){
                return toError("注册类型不能为空！");
            }
            //TODO 三方注册：先注册用户，再添加第三方登录信息
            UserInfo userInfo = userInfoService.thirdRegister(param);

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "三方注册成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "APP三方登录", notes = "APP三方登录",position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unionId", value = "第三方唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "lastLoginPlatform", value = "登录平台 1安卓 2IOS 3小程序", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/thirdLogin")
    @ResponseBody
    public String thirdLogin(UserParam param) throws IOException {
        try {
            log.info("----》APP三方登录《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getUnionId())){
                return toError("第三方唯一ID不能为空！");
            }
            if(param.getType() == null){
                return toError("注册类型不能为空！");
            }
            //TODO APP三方登录
            UserInfo userInfo = userInfoService.thirdLogin(param.getUnionId(),param.getType(),param.getLastLoginPlatform());

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "APP三方登录成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "三方绑定", notes = "三方绑定",position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "第三方唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/thirdBind")
    @ResponseBody
    public String thirdBind(UserParam param) throws IOException {
        try {
            log.info("----》三方绑定《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getUnionId())){
                return toError("三方识别号不能为空！");
            }
            //验证用户
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 三方绑定
            //三方绑定是为了将微信号与手机号绑定到一个账户，如果用户用手机号注册过，又用微信号登录了第二个账号，将没有绑定功能，而是合并账号，以手机号为主，
            // 如果用户已经存在手机号账号，并登录手机号账户，进行三方绑定，则将微信号绑定到此用户账户上
            UserInfo userInfo = userInfoService.thirdBind(param.getUserId(),param.getUnionId(),param.getType());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "三方绑定成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "实名认证", notes = "实名认证",position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "真实姓名", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份证号", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity_img_cover", value = "身份证封面图",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity_img_back", value = "身份证背面",dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/certification")
    @ResponseBody
    public String certification(UserParam param) throws IOException {
        try {
            log.info("----》实名认证《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getName())){
                return toError("用户真实姓名不能为空！");
            }
            if(StringUtils.isEmpty(param.getIdentity())){
                return toError("用户身份照号码不能为空！");
            }
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 实名认证
            //TODO 需要身份证正反照片
            userInfoExtService.certification(param.getUserId(),param.getName(),param.getIdentity(),param.getIdentityImgCover(),param.getIdentityImgBack());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "实名认证成功！");
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

//    @ApiOperation(value = "上传头像", notes = "上传头像")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "headImg", value = "头像文件，格式为：jpeg，png，jpg", required = true, dataType = "file", paramType = "query")
//    })
//    @PostMapping(value = "/headImg")
//    @ResponseBody
//    public String headImg(UserParam param) throws IOException {
//        try {
//            log.info("----》上传头像《----");
//            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
//            if (user == null) {
//                return toError("999", "登录超时，请重新登录！");
//            }
//            if (user.getStatus() == 2) {
//                return toError("999", "您的账号已被冻结，请联系管理员！");
//            }
//            //TODO 上传头像
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "上传头像成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }

    @ApiOperation(value = "完善资料", notes = "完善资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号码",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "电子邮件",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickName", value = "昵称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headImgUrl", value = "头像图片，格式为：jpeg，png，jpg",dataType = "file", paramType = "query"),
            @ApiImplicitParam(name = "bgImgUrl", value = "背景图片，格式为：jpeg，png，jpg",dataType = "file", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别，0女 1男 2不确定", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "个性签名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "联系电话", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份证号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "真实姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "payPwd", value = "支付密码，明文6位，MD5小写", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/modify")
    @ResponseBody
    public String modify(UserParam param) throws IOException {
        try {
            log.info("----》完善资料《----");
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 1.手机号不可以重复 2.用户昵称不可以重复 3.如果有正在审批昵称，不可以提交修改昵称 4.修改昵称需要审批
            if(StringUtils.isNotEmpty(param.getPhone())){
                UserInfo userByPhone = userInfoService.getUserByPhone(param.getPhone());
                if(userByPhone != null){
                    return toError("此手机号已被注册！");
                }
            }
            if(StringUtils.isNotEmpty(param.getNickName())){
                UserInfo userByNickName = userInfoService.getUserByNickName(param.getNickName());
                if(userByNickName != null){
                    return toError("昵称已存在！");
                }
                if(!param.getNickName().equals(user.getNickName())) {//用户修改了昵称
                    //查询此用户申请中的昵称
                    UserExamine userExamine = userExamineService.getUserExamineByUserId(param.getUserId());
                    if(userExamine != null){
                        if(userExamine.getStatus() == 0){
                            return toError("此昵称已在申请中……");
                        }
                    }
                }

            }else{
                return toError("用户昵称不能为空！");
            }

            //TODO 完善资料
            UserInfo userInfo = userInfoService.modify(param);
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "完善资料成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "找回密码", notes = "找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/resetPwd")
    @ResponseBody
    public String resetPwd(UserParam param) throws IOException {
        try {
            log.info("----》找回密码《----");
            log.info("参数："+param.getPhone()+"---"+param.getNewPwd());
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            if(StringUtils.isEmpty(param.getNewPwd())){
                return toError("新密码不能为空！");
            }
            //TODO 找回密码
            //修改密码
            userInfoService.resetPwd(param.getUserId(),param.getNewPwd());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "找回密码成功！");
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码，md5加密，32位小写字母", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/updatePwd")
    @ResponseBody
    public String updatePwd(UserParam param) throws IOException {
        try {
            log.info("----》修改密码《----");
            log.info("参数："+param.toString());
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //判断旧密码是否正确
            if(!MD5Util.getMD5String(param.getLoginPwd()).equals(user.getLoginPwd())){
                return toError("旧密码不正确！");
            }
            //TODO 修改密码
            userInfoService.resetPwd(param.getUserId(),param.getNewPwd());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "修改密码成功！");
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "添加收货地址", notes = "添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "receiverName", value = "收货人姓名",  required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "receiverPhone", value = "联系电话",required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "详细收货地址",  required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "postCode", value = "邮编",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1默认地址 2其他",dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/addAddress")
    @ResponseBody
    public String addAddress(UserParam param) throws IOException {
        try {
            log.info("----》添加收货地址《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getReceiverName())){
                return toError("收货人不能为空！");
            }
            if(StringUtils.isEmpty(param.getReceiverPhone())){
                return toError("联系电话不能为空！");
            }
            if(StringUtils.isEmpty(param.getProvince()) || StringUtils.isEmpty(param.getCity()) || StringUtils.isEmpty(param.getArea())){
                return toError("省市区不能为空！");
            }
            if(StringUtils.isEmpty(param.getAddress())){
                return toError("详细收货地址不能为空！");
            }
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 添加收货地址
            List<UserAddressInfo> userAddressInfos = userAddressInfoService.addAddress(param.getUserId(),param.getReceiverName(),param.getReceiverPhone(),param.getProvince(),param.getCity(),param.getArea(),param.getAddress(),param.getPostCode(),param.getType());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "添加收货地址成功！");
            result.put("userAddressInfos",userAddressInfos);
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "收货人姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "详细收货地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "联系电话",required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/updateAddress")
    @ResponseBody
    public String updateAddress(UserParam param) throws IOException {
        try {
            log.info("----》修改收货地址《----");
            log.info("参数："+param.toString());
            if(param.getAddressId()==null || param.getAddressId().intValue()==0){
                return toError("收货地址ID不能为空！");
            }
            if(StringUtils.isEmpty(param.getReceiverName())){
                return toError("收货人不能为空！");
            }
            if(StringUtils.isEmpty(param.getReceiverPhone())){
                return toError("联系电话不能为空！");
            }
            if(StringUtils.isEmpty(param.getProvince()) || StringUtils.isEmpty(param.getCity()) || StringUtils.isEmpty(param.getArea())){
                return toError("省市区不能为空！");
            }
            if(StringUtils.isEmpty(param.getAddress())){
                return toError("详细收货地址不能为空！");
            }
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 修改收货地址
            userAddressInfoService.updateAddress(param.getUserId(),param.getAddressId(),param.getReceiverName(),param.getReceiverPhone(),param.getProvince(),param.getCity(),param.getArea(),param.getAddress(),param.getPostCode(),param.getType());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "修改收货地址成功！");
            return toResult(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/deleteAddress")
    @ResponseBody
    public String deleteAddress(UserParam param) throws IOException {
        try {
            log.info("----》删除收货地址《----");
            log.info("参数："+param.toString());
            if(param.getAddressId()==null){
                return toError("收货地址ID不能为空！");
            }
            if(param.getUserId() == null){
                return toError("用户ID不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 删除收货地址
            List<UserAddressInfo> userAddressInfos = userAddressInfoService.deleteAddress(param.getUserId(),param.getAddressId());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "删除收货地址成功！");
            result.put("userAddressInfos",userAddressInfos);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }




    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/defaultAddress")
    @ResponseBody
    public String defaultAddress(UserParam param) throws IOException {
        try {
            log.info("----》设置默认收货地址《----");
            log.info("参数："+param.toString());
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 设置默认收货地址
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "设置默认收货地址成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改绑定手机", notes = "修改绑定手机")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/updatePhone")
    @ResponseBody
    public String updatePhone(UserParam param) throws IOException {
        try {
            log.info("----》修改绑定手机《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("新的手机号码不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getPhone().equals(param.getPhone())){
                return toError("不能与旧手机号相同！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            UserInfo oldUser = userInfoService.getUserByPhone(param.getPhone());
            if(oldUser!=null){
                return toError("该手机已被其他帐号绑定，请不要重复绑定！");
            }
            //TODO 修改绑定手机
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "修改绑定手机成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "我的粉丝", notes = "我的粉丝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/myFans")
    @ResponseBody
    public String myFans(UserParam param) throws IOException {
        try {
            log.info("----》我的粉丝《----");
            log.info("参数："+param.toString());
            if(param.getStart()==null){
                return toError("分页start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("分页limit不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 我的粉丝
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的粉丝成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "添加/取消关注", notes = "添加/取消关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userId ", value = "被关注用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "0关注 1取消关注", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/fansFollow")
    @ResponseBody
    public String fansFollow(UserParam param) throws IOException {
        try {
            log.info("----》添加关注/取消关注《----");
            log.info("参数："+param.toString());
            if(param.getUserId()==null || param.getUserId().intValue()==0){
                return toError("用户id不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            if(user.getId().intValue()==param.getUserId().intValue()){
                return toError("不能关注自己");
            }
            //TODO 添加/取消关注
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "添加/取消关注成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "我的关注", notes = "我的关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/myFollows")
    @ResponseBody
    public String myFollows(UserParam param) throws IOException {
        try {
            log.info("----》我的关注《----");
            log.info("参数："+param.toString());
            if(param.getStart()==null){
                return toError("分页start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("分页limit不能为空！");
            }
            //TODO 我的关注
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的关注列表成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "好友列表", notes = "好友列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/friendList")
    @ResponseBody
    public String friendList(UserParam param) throws IOException {
        try {
            log.info("----》好友列表《----");
            log.info("参数："+param.toString());
            if(param.getStart()==null){
                return toError("分页start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("分页limit不能为空！");
            }
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 好友列表
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取好友列表成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "签到", notes = "签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
    })
    @PostMapping(value = "/sign")
    @ResponseBody
    public String sign(UserParam param) throws IOException {
        try {
            log.info("----》签到《----");
            log.info("参数："+param.toString());
            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 签到
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "签到成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //测试配置文件获取
    @Value("${name:errorName}")
    String name;
    @Value("${version:errorVersion}")
    String version;
    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        return "hi,I am " + name + ",version is " + version;
    }


}
