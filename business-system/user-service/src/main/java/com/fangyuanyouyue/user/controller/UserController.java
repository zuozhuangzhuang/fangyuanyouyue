package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseClientResult;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.model.User;
import com.fangyuanyouyue.user.param.BaseParam;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.UserService;
import com.fangyuanyouyue.user.utils.MD5Util;
import com.fangyuanyouyue.user.utils.Status;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private UserService userService;


    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页", required = true, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, dataType = "Integer",paramType = "query")
    })
    @ResponseBody
    @GetMapping(value="/getList")
    public String getList(BaseParam param) throws IOException {
        try{
            log.info("----》获取用户列表《----");
            log.info("参数："+param.toString());
            if(param.getStart() == null || param.getLimit() == null){
                return toError("分页参数异常！");
            }
            List<User> a_users = userService.getList(param.getStart(),param.getLimit());
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"请求成功！");
            result.put("a_users",a_users);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
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
            //假设成功
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
            //假设成功
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"注册成功！");
            result.put("param",param);
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
            //假设成功
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"注册成功！");
            result.put("param",param);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

}
