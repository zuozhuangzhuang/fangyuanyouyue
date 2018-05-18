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
public class UserController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Value("${name}")
    String name;
    @Value("${version}")
    String version;

    @RequestMapping("/hi")
    @ResponseBody
    public String hi(){
        return "hi,I am "+name;
    }

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String",paramType = "query"),
        @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String",paramType = "query")
    })
    @PostMapping(value="/login")
    @ResponseBody
    public String login(UserParam param) throws IOException {
        try{
            log.info("----》用户登录《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getLoginPwd())){
                return toError("密码不能为空！");
            }
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 假设成功
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"登陆成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value="注册", notes="注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String",paramType = "query")
    })
    @PostMapping(value="/regist")
    @ResponseBody
    public String regist(UserParam param) throws IOException {
        try{
            log.info("----》注册《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getLoginPwd())){
                return toError("登录密码不能为空！");
            }
            User a_user = userService.getUserByPhone(param.getPhone());
            if(a_user != null){
                return toError("手机号码已被注册！");
            }
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 假设成功
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"注册成功！");
//            result.put("param",param);
            result.put("name",name);
            result.put("version",version);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value="实名认证", notes="实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "loginPwd", value = "登录密码", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cardNo", value = "身份证号", required = true, dataType = "String",paramType = "query")
    })
    @PostMapping(value="/modify")
    @ResponseBody
    public String modify(UserParam param) throws IOException {
        try{
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
            User user= userService.getUserByToken(param.getToken());
            if(user==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            //MD5加密
            param.setLoginPwd(MD5Util.getMD5String(param.getLoginPwd()));
            //TODO 假设成功
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"实名认证成功！");
            result.put("param",param);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }



}
