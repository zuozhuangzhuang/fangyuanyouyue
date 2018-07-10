package com.fangyuanyouyue.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.dto.UserAddressDto;
import com.fangyuanyouyue.user.dto.UserDto;
import com.fangyuanyouyue.user.model.UserExamine;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.model.WeChatSession;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.*;
import com.fangyuanyouyue.user.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private SchedualGoodsService schedualGoodsService;//调用goods-service
    @Autowired
    private SchedualMessageService schedualMessageService;//message-service
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "注册", notes = "注册",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码,MD5小写",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickName", value = "昵称",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headImg", value = "头像图片，格式为：jpeg，png，jpg", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "bgImg", value = "背景图片", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别，1男 2女 0不确定", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "regPlatform", value = "注册平台 1安卓 2iOS 3小程序", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/regist")
    @ResponseBody
    public String regist(UserParam param) throws IOException {
        try {
            log.info("----》注册《----");
            log.info("参数：" + param.toString());
            if(param.getRegPlatform() == null){
                return toError(ReCode.FAILD.getValue(),"注册平台不能为空！");
            }
            if (StringUtils.isEmpty(param.getPhone())) {
                return toError(ReCode.FAILD.getValue(),"手机号码不能为空！");
            }
            if (StringUtils.isEmpty(param.getLoginPwd())) {
                return toError(ReCode.FAILD.getValue(),"登录密码不能为空！");
            }
            if (StringUtils.isEmpty(param.getNickName())) {
                return toError(ReCode.FAILD.getValue(),"用户昵称不能为空！");
            }
            UserInfo userInfo = userInfoService.getUserByPhone(param.getPhone());
            if (userInfo != null) {
                return toError(ReCode.FAILD.getValue(),"手机号码已被注册！");
            }
            //注册
            UserDto userDto = userInfoService.regist(param);
            return toSuccess(userDto,"注册成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户登录",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码,MD5小写", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPlatform", value = "登录平台 1安卓 2iOS 3小程序", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(UserParam param) throws IOException {
        try {
            log.info("----》用户登录《----");
            log.info("参数：" + param.toString());
            if (StringUtils.isEmpty(param.getPhone())) {
                return toError(ReCode.FAILD.getValue(),"手机号码不能为空！");
            }
            if (StringUtils.isEmpty(param.getLoginPwd())) {
                return toError(ReCode.FAILD.getValue(),"密码不能为空！");
            }
            if(param.getLoginPlatform() == null){
                return toError(ReCode.FAILD.getValue(),"登录平台不能为空！");
            }
            //MD5加密
//            param.setLoginPwd(MD5Util.generate(MD5Util.MD5(param.getLoginPwd())));
            //用户登录
            UserDto userDto = userInfoService.login(param.getPhone(),param.getLoginPwd(),param.getLoginPlatform());
            return toSuccess(userDto,"登录成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "APP三方注册/登录", notes = "APP三方注册/登录",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thirdNickName", value = "第三方账号昵称", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdHeadImgUrl", value = "第三方账号头像地址",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别，1男 2女 0不确定", required = true,dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "loginPlatform", value = "登录平台 1安卓 2iOS 3小程序",required = true,  dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "第三方唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "regPlatform", value = "注册平台 1安卓 2iOS 3小程序",required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping(value = "/thirdLogin")
    @ResponseBody
    public String thirdLogin(UserParam param) throws IOException {
        try {
            log.info("----》APP三方注册/登录《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getUnionId())){
                return toError("第三方唯一ID不能为空！");
            }
            if(param.getType() == null){
                return toError("三方类型不能为空！");
            }
            //APP三方注册/登录
            param.setRegType(1);//注册来源 1app 2微信小程序
            UserDto userDto = userInfoService.thirdLogin(param);
            return toSuccess(userDto,"APP三方注册/登录成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "三方绑定", notes = "三方绑定",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "第三方唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博", required = true, dataType = "int", paramType = "query")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            //验证用户
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //三方绑定
            //三方绑定是为了将微信号与手机号绑定到一个账户，如果用户用手机号注册过，又用微信号登录了第二个账号，将没有绑定功能，而是合并账号，以手机号为主，
            // 如果用户已经存在手机号账号，并登录手机号账户，进行三方绑定，则将微信号绑定到此用户账户上
            UserDto userDto = userInfoService.thirdBind(param.getToken(),param.getUnionId(),param.getType());
            return toSuccess(userDto,"三方绑定成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "实名认证", notes = "实名认证",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "真实姓名", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份证号", required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity_img_cover", value = "身份证封面图",dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "identity_img_back", value = "身份证背面",dataType = "file", paramType = "form")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //实名认证
            userInfoExtService.certification(param.getToken(),param.getName(),param.getIdentity(),param.getIdentityImgCover(),param.getIdentityImgBack());
            return toSuccess("实名认证成功");
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
//                return toError(ReCode.FAILD.getValue(), "登录超时，请重新登录！");
//            }
//            if (user.getStatus() == 2) {
//                return toError(ReCode.FAILD.getValue(), "您的账号已被冻结，请联系管理员！");
//            }
//            //TODO 上传头像
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "上传头像成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }

    @ApiOperation(value = "完善资料", notes = "完善资料",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号码",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "email", value = "电子邮件",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userAddress", value = "用户所在地",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickName", value = "昵称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headImg", value = "头像图片，格式为：jpeg，png，jpg",dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "bgImg", value = "背景图片，格式为：jpeg，png，jpg",dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别，1男 2女 0不确定", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "个性签名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "联系电话", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identity", value = "身份证号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "真实姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "payPwd", value = "支付密码，md5加密，32位小写字母", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/modify")
    @ResponseBody
    public String modify(UserParam param) throws IOException {
        try {
            log.info("----》完善资料《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //手机号不可以重复
            if(StringUtils.isNotEmpty(param.getPhone())){
                UserInfo userByPhone = userInfoService.getUserByPhone(param.getPhone());
                if(userByPhone != null){
                    return toError("此手机号已被注册！");
                }
            }
            //用户昵称不可以重复
            if(StringUtils.isNotEmpty(param.getNickName())){
                UserInfo userByNickName = userInfoService.getUserByNickName(param.getNickName());
                if(userByNickName != null){
                    return toError("昵称已存在！");
                }
                if(!param.getNickName().equals(user.getNickName())) {//用户修改了昵称
                    //查询此用户申请中的昵称
                    //如果有正在审批昵称，不可以提交修改昵称 TODO 4.修改昵称需要审批
                    UserExamine userExamine = userExamineService.getUserExamineByUserId(param.getToken());
                    if(userExamine != null){
                        if(userExamine.getStatus() == 0){
                            return toError("此昵称已在申请中，请勿重复提交");
                        }
                    }
                }
            }
            //TODO 完善资料
            UserDto userDto = userInfoService.modify(param);
            return toSuccess(userDto,"完善资料成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "找回密码", notes = "找回密码",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/resetPwd")
    @ResponseBody
    public String resetPwd(UserParam param) throws IOException {
        try {
            log.info("----》找回密码《----");
            log.info("参数："+param.getPhone()+"---"+param.getNewPwd());
            if(param.getPhone() == null){
                return toError("用户手机不能为空！");
            }
            if(StringUtils.isEmpty(param.getNewPwd())){
                return toError("新密码不能为空！");
            }
            //TODO 找回密码
            //修改密码
            userInfoService.resetPwd(param.getPhone(),param.getNewPwd());
            return toSuccess("找回密码成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码，md5加密，32位小写字母", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/updatePwd")
    @ResponseBody
    public String updatePwd(UserParam param) throws IOException {
        try {
            log.info("----》修改密码《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            if(StringUtils.isEmpty(user.getPhone())){
                return toError("第三方用户不可修改密码！");
            }
            //判断旧密码是否正确
//            if(!MD5Util.MD5(param.getLoginPwd()).equals(user.getLoginPwd())){
            if(!MD5Util.verify(MD5Util.MD5(param.getLoginPwd()),user.getLoginPwd())){
                return toError("旧密码不正确！");
            }
            //TODO 修改密码
            userInfoService.updatePwd(param.getToken(),param.getNewPwd());
            return toSuccess("修改密码成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "添加收货地址", notes = "添加收货地址",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "receiverName", value = "收货人姓名",  required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "receiverPhone", value = "联系电话",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区",required = true, dataType = "String", paramType = "query"),
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //TODO 添加收货地址
            List<UserAddressDto> userAddressDtos = userAddressInfoService.addAddress(param.getToken(),param.getReceiverName(),param.getReceiverPhone(),param.getProvince(),param.getCity(),param.getArea(),param.getAddress(),param.getPostCode(),param.getType());
            ResultUtil resultUtil = new ResultUtil(0,"请求成功",new Date(),userAddressDtos,"添加收货地址成功");
            return toResult(resultUtil);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "receiverName", value = "收货人姓名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "receiverPhone", value = "联系电话",required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "详细收货地址", dataType = "String", paramType = "query")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //TODO 修改收货地址
            userAddressInfoService.updateAddress(param.getToken(),param.getAddressId(),param.getReceiverName(),param.getReceiverPhone(),param.getProvince(),param.getCity(),param.getArea(),param.getAddress(),param.getPostCode(),param.getType());
            return toSuccess("修改收货地址成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "int", paramType = "query")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user==null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //TODO 删除收货地址
            List<UserAddressDto> userAddressDtos = userAddressInfoService.deleteAddress(param.getToken(),param.getAddressId());
            ResultUtil resultUtil = new ResultUtil(0,"请求成功",new Date(),userAddressDtos,"删除收货地址成功");
            return toResult(resultUtil);
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }




    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/defaultAddress")
    @ResponseBody
    public String defaultAddress(UserParam param) throws IOException {
        try {
            log.info("----》设置默认收货地址《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user = userInfoService.getUserByToken(param.getToken());
            if(user == null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //TODO 设置默认收货地址
            userAddressInfoService.defaultAddress(param.getToken(),param.getAddressId());
            return toSuccess("设置默认收货地址成功！");
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改绑定手机", notes = "修改绑定手机",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user == null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            if(user.getPhone() != null && !user.getPhone().equals("")){
                if(user.getPhone().equals(param.getPhone())){
                    return toError("不能与旧手机号相同！");
                }
            }
            UserInfo oldUser = userInfoService.getUserByPhone(param.getPhone());
            if(oldUser != null){
                return toError("该手机已被其他帐号绑定，请不要重复绑定！");
            }
            //TODO 修改绑定手机
            UserDto userDto = userInfoService.updatePhone(param.getToken(),param.getPhone());
            return toSuccess(userDto,"修改绑定手机成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "合并账号", notes = "合并账号",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/accountMerge")
    @ResponseBody
    public String accountMerge(UserParam param) throws IOException{
        try {
            log.info("----》合并账号《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            UserInfo user=userInfoService.getUserByToken(param.getToken());
            if(user == null){
                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
            }
            if(user.getStatus() == 2){
                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
            }
            //TODO 合并账号
            UserDto userDto = userInfoService.accountMerge(param.getToken(),param.getPhone());
            return toSuccess(userDto,"修改绑定手机成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "小程序登录", notes = "小程序登录",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code值", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdNickName", value = "第三方账号昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdHeadImgUrl", value = "第三方账号头像地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别，1男 2女 0不确定", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1微信 2QQ 3微博",required = true,  dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "encryptedData", value = "包括敏感数据在内的完整用户信息的加密数据", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "加密算法的初始向量", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/miniLogin")
    @ResponseBody
    public String miniLogin(UserParam param) throws IOException{
        try{
            log.info("----》小程序登录《----");
            log.info("参数："+param.toString());
            //微信的接口
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+WeChatSession.APPID+
                    "&secret="+WeChatSession.SECRET+"&js_code="+ param.getCode() +"&grant_type=authorization_code";
            RestTemplate restTemplate = new RestTemplate();
            //进行网络请求,访问url接口
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            //根据返回值进行后续操作
            if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK){
                String sessionData = responseEntity.getBody();
                //解析从微信服务器获得的openid和session_key;
                WeChatSession weChatSession = JSONObject.parseObject(sessionData,WeChatSession.class);
                if(weChatSession == null){
                    return toError(ReCode.FAILD.getValue(),"页面授权失败！");
                }
                //微信用户在此小程序的识别号
                String openid = weChatSession.getOpenid();
                //获取会话秘钥
                String session_key = weChatSession.getSession_key();
                //TODO 正常情况只返回openID和session_key，注册过的用户会额外返回unionID
                //微信用户的固定唯一识别号
                String unionid = weChatSession.getUnionid();
                if(StringUtils.isEmpty(unionid)){
                    //获取不到unionId，根据算法解密得到unionID
                    // 被加密的数据
                    byte[] dataByte = Base64.decodeBase64(weChatSession.getEncryptedData());
                    // 加密秘钥
                    byte[] aeskey = Base64.decodeBase64(session_key);
                    // 偏移量
                    byte[] ivByte = Base64.decodeBase64(weChatSession.getIv());
                    String newuserInfo;
                    try {
                        //AES解密
                        AES aes = new AES();
                        byte[] resultByte = aes.decrypt(dataByte, aeskey, ivByte);
                        if (null != resultByte && resultByte.length > 0) {
                            newuserInfo = new String(resultByte, "UTF-8");
                            log.info("解密完毕,解密结果为newuserInfo:"+ newuserInfo);
                            JSONObject jsonObject = JSONObject.parseObject(newuserInfo);
                            unionid = jsonObject.getString("unionid");
                            param.setUnionId(unionid);
                            UserDto userDto = userInfoService.miniLogin(param,openid,session_key);
                            return toSuccess(userDto,"小程序登录成功！");
                        }else{
                            return toError("解密异常!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return toError("解密异常!检查解密数据！");
                    }
                }else{
                    //获取到unionId，注册/登录
                    param.setUnionId(unionid);
                    UserDto userDto = userInfoService.miniLogin(param,openid,session_key);
                    //最后要返回一个自定义的登录态,用来做后续数据传输的验证
                    return toSuccess(userDto,"小程序登录成功！");
                }
            }else{
                return toError(ReCode.FAILD.getValue(),"页面授权失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "发送验证码", notes = "发送验证码",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "验证码类型 0表示注册 1表示密码找回 2 表示支付密码相关 3验证旧手机，4绑定新手机", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "三方唯一识别号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdType", value = "类型 1微信 2QQ 3微博", dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/sendCode")
    @ResponseBody
    public String sendCode(UserParam param) throws IOException {
        try {
            log.info("----》发送验证码《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError(ReCode.FAILD.getValue(),"手机号码不能为空！");
            }
            if(param.getType() == null){
                return toError("类型不能为空！");
            }
            //验证用户
            //根据手机号获取用户，如果存在，则说明为旧手机号,调用user-service
            UserInfo userInfo=userInfoService.getUserByPhone(param.getPhone());
            if(PhoneCode.TYPE_REGIST.getCode() == param.getType()){//使用手机号注册新用户
                if(userInfo != null){
                    return toError("此手机号已被注册！");
                }
            }else if(PhoneCode.TYPE_NEW_PHONE.getCode() == param.getType()){//TODO 未启用 绑定新手机
                if(userInfo != null){
                    return toError("此手机号已被注册！");
                }
            }else if(PhoneCode.TYPE_OLD_PHONE.getCode() == param.getType()){//为3验证旧手机
                if(userInfo != null){//已存在此手机号
                    //验证此手机是否存在其他识别号
                    if(StringUtils.isNotEmpty(param.getUnionId())){//根据传入参数判断是qq还是微信绑定

                    }
                }
            }else{
				/*if(count == 0){
					return toError("此手机号尚未注册！");
				}*/
            }
            //调用短信系统发送短信
            JSONObject jsonObject = JSONObject.parseObject(schedualMessageService.sendCode(param.getPhone(),param.getType()));
            String code = jsonObject.getString("data");
            log.info("code:"+code);

            redisTemplate.opsForValue().set(param.getPhone(),code);
            redisTemplate.expire(param.getPhone(),1,TimeUnit.DAYS);
            return toSuccess("发送验证码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "验证验证码", notes = "验证验证码",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/verifyCode")
    @ResponseBody
    public String verifyCode(UserParam param) throws IOException {

        try {
            log.info("----》验证验证码《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getCode())){
                return toError("验证码不能为空！");
            }
            String code = (String) redisTemplate.opsForValue().get(param.getPhone());
            log.info("验证码:1."+code+" 2."+param.getCode());
            if(StringUtils.isEmpty(code) || !code.equals(param.getCode())){
                return toError("验证码错误！");
            }


            return toSuccess("验证验证码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
//    @ApiOperation(value = "我的粉丝", notes = "我的粉丝")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
//    })
//    @PostMapping(value = "/myFans")
//    @ResponseBody
//    public String myFans(UserParam param) throws IOException {
//        try {
//            log.info("----》我的粉丝《----");
//            log.info("参数："+param.toString());
//            if(param.getStart()==null){
//                return toError("分页start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("分页limit不能为空！");
//            }
//            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
//            if(user==null){
//                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
//            }
//            if(user.getStatus() == 2){
//                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
//            }
//            //TODO 我的粉丝
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的粉丝成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//
//    @ApiOperation(value = "添加/取消关注", notes = "添加/取消关注")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "userId ", value = "被关注用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "type", value = "0关注 1取消关注", required = true, dataType = "Integer", paramType = "query")
//    })
//    @PostMapping(value = "/fansFollow")
//    @ResponseBody
//    public String fansFollow(UserParam param) throws IOException {
//        try {
//            log.info("----》添加关注/取消关注《----");
//            log.info("参数："+param.toString());
//            if(param.getUserId()==null || param.getUserId().intValue()==0){
//                return toError("用户id不能为空！");
//            }
//            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
//            if(user==null){
//                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
//            }
//            if(user.getStatus() == 2){
//                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
//            }
//            if(user.getId().intValue()==param.getUserId().intValue()){
//                return toError("不能关注自己");
//            }
//            //TODO 添加/取消关注
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "添加/取消关注成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    @ApiOperation(value = "我的关注", notes = "我的关注")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
//    })
//    @PostMapping(value = "/myFollows")
//    @ResponseBody
//    public String myFollows(UserParam param) throws IOException {
//        try {
//            log.info("----》我的关注《----");
//            log.info("参数："+param.toString());
//            if(param.getStart()==null){
//                return toError("分页start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("分页limit不能为空！");
//            }
//            //TODO 我的关注
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的关注列表成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    @ApiOperation(value = "好友列表", notes = "好友列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "Integer", paramType = "query")
//    })
//    @PostMapping(value = "/friendList")
//    @ResponseBody
//    public String friendList(UserParam param) throws IOException {
//        try {
//            log.info("----》好友列表《----");
//            log.info("参数："+param.toString());
//            if(param.getStart()==null){
//                return toError("分页start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("分页limit不能为空！");
//            }
//            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
//            if(user==null){
//                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
//            }
//            if(user.getStatus() == 2){
//                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
//            }
//            //TODO 好友列表
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取好友列表成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    @ApiOperation(value = "签到", notes = "签到")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query"),
//    })
//    @PostMapping(value = "/sign")
//    @ResponseBody
//    public String sign(UserParam param) throws IOException {
//        try {
//            log.info("----》签到《----");
//            log.info("参数："+param.toString());
//            UserInfo user=userInfoService.selectByPrimaryKey(param.getUserId());
//            if(user==null){
//                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
//            }
//            if(user.getStatus() == 2){
//                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
//            }
//            //TODO 签到
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "签到成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }

    //测试配置文件获取
//    @Value("${name:errorName}")
//    String name;
//    @Value("${version:errorVersion}")
//    String version;
//    @RequestMapping("/hi")
//    @ResponseBody
//    public String hi() {
//        return "hi,I am " + name + ",version is " + version;
//    }


    public static void main(String[] args) {
        String code = "023dDxiJ1b2Hv60OxjmJ156AiJ1dDxib";
        String encryptedData = "3f1eqRRy71lnE4hLzoO+FLOylSO37WM5eXg0H3+rnW3Oe7orwmb6/hEQ+XUPAWNppZQM6WXv4acVO0kUco/12IU9asMhBKf3ljZrkZqVKJnIBQb8cmWCvy/uLpD7/YwnOkrAFou61LUGccRvJGwSeI3MKdOix+9oHxvl4KLLTHt52hl4iwW4zRsG1J6bKnXcZt27N4+84QNdIW85SKNrtuOleFUBw74ThKRp3P7F8w/0Azbp9mScNT3ZUkAeQ106tQqn4/pcSjgRJ69tuS5Soqx6T6JeiDUF4coOCr369Dg5R95wLZ6rBJ0YZvk3hF5hmJfI0Slfis+WHC1oSMrzCmpHiaVDGkoKfvJpIEO6CJ+I94W28UhHWjJ2oHz8lAdzS0goj/Jv7Dko10t4hlObAzpGLXpD93/sVXNWL7GGCyE6ZRe1p7e+5KKybdsNvHcagiR/12mTZDZjMhJC3fXjN+s0kN4WQySVhqFDCUHdT+Q7+av5WiIxor/LSbbdu6LUSKwk27A9g6z/EtOwSfYkA3d732CXkraaYRg8qyN1bYg=";
        String iv = "Gsn0F1c1yAO5DFS7TLk2kw==";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+WeChatSession.APPID+
                "&secret="+WeChatSession.SECRET+"&js_code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        //进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        System.out.println(responseEntity);
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            WeChatSession weChatSession = JSONObject.parseObject(sessionData,WeChatSession.class);
            //微信用户在此小程序的识别号
            String openid = weChatSession.getOpenid();
            //获取会话秘钥
            String session_key = weChatSession.getSession_key();
            System.out.println("openid:"+openid);
            System.out.println("session_key:"+session_key);
            // 被加密的数据
            byte[] dataByte = Base64.decodeBase64(encryptedData);
            // 加密秘钥
            byte[] aeskey = Base64.decodeBase64(session_key);
            // 偏移量
            byte[] ivByte = Base64.decodeBase64(iv);
            String newuserInfo = "";
            try {
                AES aes = new AES();
                byte[] resultByte = aes.decrypt(dataByte, aeskey, ivByte);
                if (null != resultByte && resultByte.length > 0) {
                    newuserInfo = new String(resultByte, "UTF-8");
                    System.out.println("解密完毕,解密结果为newuserInfo:"+ newuserInfo);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
