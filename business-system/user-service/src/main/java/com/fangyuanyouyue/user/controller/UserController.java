package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseClientResult;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.model.User;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserService;
import com.fangyuanyouyue.user.utils.MD5Util;
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

@Controller
@RequestMapping(value = "/user")
@Api(description = "用户系统Controller")
@RefreshScope
public class UserController extends BaseController {
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Value("${name}")
    String name;
    @Value("${version}")
    String version;

    @RequestMapping("/hi")
    @ResponseBody
    public String hi() {
        return "hi,I am " + name;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String", paramType = "query")
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
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 用户登录
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "登陆成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/regist")
    @ResponseBody
    public String regist(UserParam param) throws IOException {
        try {
            log.info("----》注册《----");
            log.info("参数：" + param.toString());
            if (StringUtils.isEmpty(param.getPhone())) {
                return toError("手机号码不能为空！");
            }
            if (StringUtils.isEmpty(param.getLoginPwd())) {
                return toError("登录密码不能为空！");
            }
            User a_user = userService.getUserByPhone(param.getPhone());
            if (a_user != null) {
                return toError("手机号码已被注册！");
            }
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 注册
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "注册成功！");
//            result.put("param",param);
            result.put("name", name);
            result.put("version", version);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "实名认证", notes = "实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardNo", value = "身份证号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/certification")
    @ResponseBody
    public String certification(UserParam param) throws IOException {
        try {
            log.info("----》实名认证《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(StringUtils.isEmpty(param.getRealName())){
                return toError("用户真实姓名不能为空！");
            }
            if(StringUtils.isEmpty(param.getCardNo())){
                return toError("用户身份照号码不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 实名认证
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "实名认证成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "上传头像", notes = "上传头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headImg", value = "头像文件，格式为：jpeg，png，jpg", required = true, dataType = "file", paramType = "query")
    })
    @PostMapping(value = "/headImg")
    @ResponseBody
    public String headImg(UserParam param) throws IOException {
        try {
            log.info("----》上传头像《----");
            if (StringUtils.isEmpty(param.getToken())) {
                return toError("用户token不能为空！");
            }
            User user = userService.getUserByToken(param.getToken());
            if (user == null) {
                return toError("999", "登录超时，请重新登录！");
            }
            if (StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())) {
                return toError("999", "您的账号已被冻结，请联系管理员！");
            }
            //TODO 上传头像
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "上传头像成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "完善资料", notes = "完善资料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别,0女,1男", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "payPwd", value = "支付密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "birth", value = "出生日期", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "autograph", value = "个性签名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地区", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contact", value = "联系电话", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/modify")
    @ResponseBody
    public String modify(UserParam param) throws IOException {
        try {
            log.info("----》完善资料《----");
            if (StringUtils.isEmpty(param.getToken())) {
                return toError("用户token不能为空！");
            }
            User user = userService.getUserByToken(param.getToken());
            if (user == null) {
                return toError("999", "登录超时，请重新登录！");
            }
            if (StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())) {
                return toError("999", "您的账号已被冻结，请联系管理员！");
            }
            //TODO 完善资料
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "完善资料成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "找回密码", notes = "找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户电话", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/resetPwd")
    @ResponseBody
    public String resetPwd(UserParam param) throws IOException {
        try {
            log.info("----》找回密码《----");
            log.info("参数："+param.getPhone()+"---"+param.getNewPwd());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("电话号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getNewPwd())){
                return toError("新密码不能为空！");
            }
            //修改密码
//            userService.updateByPrimaryKey(param);
            //TODO 找回密码
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "找回密码成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码，md5加密，32位小写字母", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPwd", value = "新密码密码，md5加密，32位小写字母",required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/updatePwd")
    @ResponseBody
    public String updatePwd(UserParam param) throws IOException {
        try {
            log.info("----》修改密码《----");
            log.info("参数："+param.toString());
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            //判断旧密码是否正确
            //修改密码
            //TODO 修改密码
//            userService.updateByPrimaryKey(param);
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "修改密码成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "添加收货地址", notes = "添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "收货人姓名",  required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "联系电话",required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "详细收货地址",  required = true,dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/addAddress")
    @ResponseBody
    public String addAddress(UserParam param) throws IOException {
        try {
            log.info("----》添加收货地址《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(StringUtils.isEmpty(param.getUserName())){
                return toError("收货人不能为空！");
            }
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("联系电话不能为空！");
            }
            if(StringUtils.isEmpty(param.getAddress())){
                return toError("收货地址不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 添加收货地址
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "添加收货地址成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = true, dataType = "int", paramType = "query"),
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getAddressId()==null || param.getAddressId().intValue()==0){
                return toError("收货地址ID不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 修改收货地址
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "修改收货地址成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
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
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getAddressId()==null || param.getAddressId().intValue()==0){
                return toError("收货地址ID不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 删除收货地址
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "删除收货地址成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址")
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
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
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
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/updatePhone")
    @ResponseBody
    public String updatePhone(UserParam param) throws IOException {
        try {
            log.info("----》修改绑定手机《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("token不能为空！");
            }
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("新的手机号码不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(user.getPhone().equals(param.getPhone())){
                return toError("不能与旧手机号相同！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            User oldUser = userService.getUserByPhone(param.getPhone());
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

    @ApiOperation(value = "三方注册", notes = "三方注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "headUrl", value = "头像地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别0女1男", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qqCliend", value = "qq唯一识别号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "wechatCliend", value = "微信唯一识别号", dataType = "String", paramType = "query")
//            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "loginPwd", value = "登录密码(MD5加密)", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/thirdRegister")
    @ResponseBody
    public String thirdRegister(UserParam param) throws IOException {
        try {
            log.info("----》三方注册《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getUserName())){
                return toError("三方用户名不能为空！");
            }
            if(StringUtils.isEmpty(param.getGender())){
                return toError("性别不能为空！");
            }
            /*if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号不能为空！");
            }
            if(StringUtils.isEmpty(param.getLoginPwd())){
                return toError("登录密码不能为空！");
            }*/
            if(StringUtils.isEmpty(param.getHeadUrl())){
                return toError("头像地址不能为空！");
            }
            if(StringUtils.isNotEmpty(param.getQqCliend()) && StringUtils.isNotEmpty(param.getWechatCliend())){
                return toError("不能同时传递两个三方识别号！");
            }
            if(StringUtils.isEmpty(param.getQqCliend()) && StringUtils.isEmpty(param.getWechatCliend())){
                return toError("三方号不能为空！");
            }
            //TODO 三方注册
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "三方注册成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "APP三方登录", notes = "APP三方登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thirdNo", value = "三方唯一识别号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "三方类型 0 QQ  1微信", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/thirdLogin")
    @ResponseBody
    public String thirdLogin(UserParam param) throws IOException {
        try {
            log.info("----》APP三方登录《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getThirdNo())){
                return toError("3","三方识别号不能为空！");
            }
            if(StringUtils.isEmpty(param.getType())){
                return toError("3","三方识别号类型不能为空！");
            }
            //TODO APP三方登录
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "APP三方登录成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "三方绑定", notes = "三方绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thirdNo", value = "三方唯一识别号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "三方类型 0 QQ  1微信", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/thirdBind")
    @ResponseBody
    public String thirdBind(UserParam param) throws IOException {
        try {
            log.info("----》三方绑定《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getThirdNo())){
                return toError("三方识别号不能为空！");
            }
            if(StringUtils.isEmpty(param.getType())){
                return toError("三方识别号类型不能为空！");
            }
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //TODO 三方绑定
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "三方绑定成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "我的粉丝", notes = "我的粉丝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/myFans")
    @ResponseBody
    public String myFans(UserParam param) throws IOException {
        try {
            log.info("----》我的粉丝《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getStart()==null){
                return toError("分页start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("分页limit不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
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
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId ", value = "被关注用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "0关注 1取消关注", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/fansFollow")
    @ResponseBody
    public String fansFollow(UserParam param) throws IOException {
        try {
            log.info("----》添加关注/取消关注《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getUserId()==null || param.getUserId().intValue()==0){
                return toError("用户id不能为空！");
            }
            if(StringUtils.isEmpty(param.getType())){
                return toError("操作类型不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
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
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/myFollows")
    @ResponseBody
    public String myFollows(UserParam param) throws IOException {
        try {
            log.info("----》我的关注《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
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
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start ", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/friendList")
    @ResponseBody
    public String friendList(UserParam param) throws IOException {
        try {
            log.info("----》好友列表《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getStart()==null){
                return toError("分页start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("分页limit不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
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
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/sign")
    @ResponseBody
    public String sign(UserParam param) throws IOException {
        try {
            log.info("----》签到《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            User user=userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
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

}
