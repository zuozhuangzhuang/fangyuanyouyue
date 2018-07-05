package com.fangyuanyouyue.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.sms.client.BaseController;
import com.fangyuanyouyue.sms.param.SmsParam;
import com.fangyuanyouyue.sms.service.SchedualUserService;
import com.fangyuanyouyue.sms.service.SmsService;
import com.fangyuanyouyue.sms.utils.CheckCode;
import com.fangyuanyouyue.sms.utils.PhoneCode;
import com.fangyuanyouyue.sms.utils.ReCode;
import com.fangyuanyouyue.sms.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping(value = "/sms")
@Api(description = "商品系统Controller")
@RefreshScope
public class SmsController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用
    @Autowired
    private SmsService smsService;


    @ApiOperation(value = "发送验证码", notes = "发送验证码",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "发布用户id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "验证码类型 0表示注册 1表示密码找回 2 表示支付密码相关 3验证旧手机，4绑定新手机", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "unionId", value = "三方唯一识别号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "thirdType", value = "类型 1微信 2QQ 3微博", dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/sendCode")
    @ResponseBody
    public String sendCode(SmsParam param, HttpSession session) throws IOException {
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
//            if(param.getUserId() != null){
//                return toError("用户id不能为空！");
//            }
//            String verifyUserById = schedualGoodsService.verifyUserById(param.getUserId());
//            JSONObject jsonObject = JSONObject.parseObject(verifyUserById);
//            JSONObject user = JSONObject.parseObject(jsonObject.getString("userInfo"));
//            if(user==null){
//                return toError("登录超时，请重新登录！");
//            }
//            if((int)user.get("status") == 2){
//                return toError("您的账号已被冻结，请联系管理员！");
//            }
            //根据手机号获取用户，如果存在，则说明为旧手机号,调用user-service
            String verifyUserByPhone = schedualUserService.verifyUserByPhone(param.getPhone());
            JSONObject jsonObject = JSONObject.parseObject(verifyUserByPhone);
            JSONObject userInfo = JSONObject.parseObject(jsonObject.getString("userInfo"));

            if(PhoneCode.TYPE_REGIST.getValue().equals(param.getType())){//使用手机号注册新用户
                if(userInfo != null){
                    return toError("此手机号已被注册！");
                }else{
                    //发送验证码
                }
            }else if(PhoneCode.TYPE_NEW_PHONE.getValue().equals(param.getType())){//TODO 未启用 绑定新手机
                if(userInfo != null){
                    return toError("此手机号已被注册！");
                }else{
                    //发送验证码
                }
            }else if(PhoneCode.TYPE_OLD_PHONE.getValue().equals(param.getType())){//为3验证旧手机
                if(userInfo != null){//已存在此手机号
                    //验证此手机是否存在其他识别号
                    if(StringUtils.isNotEmpty(param.getUnionId())){//根据传入参数判断是qq还是微信绑定
//                        if(user.getQqCliend() != null){//此手机号已绑定其他QQ识别号
//                            return toError("此手机号已绑定其他QQ账号！");
//                        }else{//此手机号未绑定QQ识别号，发送验证码
//
//                        }
                    }
                }else{//未存在此手机号
                    //手机号为新手机号
                    //发送验证码进行绑定
                }
            }else{
				/*if(count == 0){
					return toError("此手机号尚未注册！");
				}*/
            }
            String code = CheckCode.getCheckCode();
            smsService.sendCode(param.getPhone(), code, "");
            session.setAttribute(PhoneCode.KEY_REGIST.getValue(), code);
            //区分类型意义？
			/*if(PhoneCode.TYPE_REGIST.getValue().equals(param.getType())){
				session.setAttribute(PhoneCode.KEY_REGIST.getValue(), code);
			}else if(PhoneCode.TYPE_FINDPWD.getValue().equals(param.getType())){
				session.setAttribute(PhoneCode.KEY_FINDPWD.getValue(), code);
			}else if(PhoneCode.TYPE_OLD_PHONE.getValue().equals(param.getType())){
				session.setAttribute(PhoneCode.KEY_OLD_PHONE.getValue(), code);
			}else if(PhoneCode.TYPE_NEW_PHONE.getValue().equals(param.getType())){
				session.setAttribute(PhoneCode.KEY_NEW_PHONE.getValue(), code);
			}else if(PhoneCode.TYPE_SET_PAY_PWD.getValue().equals(param.getType())){
				session.setAttribute(PhoneCode.KEY_SET_PAY_PWD.getValue(), code);
			}*/
            session.setAttribute("phone", param.getPhone());
            session.setMaxInactiveInterval(60);//60秒失效
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
    public String verifyCode(SmsParam param,HttpSession session) throws IOException {

        try {
            log.info("----》验证验证码《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getPhone())){
                return toError("手机号码不能为空！");
            }
            if(StringUtils.isEmpty(param.getCode())){
                return toError("验证码不能为空！");
            }

			/*String code = null ;
			//判断验证码类型:
			if(session.getAttribute(PhoneCode.KEY_REGIST.getValue()) != null){//0注册
				code = (String)session.getAttribute(PhoneCode.KEY_REGIST.getValue());
			}else if(session.getAttribute(PhoneCode.KEY_FINDPWD.getValue()) != null){//1找回密码
				code = (String)session.getAttribute(PhoneCode.KEY_FINDPWD.getValue());
			}else if(session.getAttribute(PhoneCode.KEY_SET_PAY_PWD.getValue()) != null){//2设置/修改支付密码
				code = (String)session.getAttribute(PhoneCode.KEY_SET_PAY_PWD.getValue());
			}else if(session.getAttribute(PhoneCode.KEY_OLD_PHONE.getValue()) != null){//3验证旧手机
				code = (String)session.getAttribute(PhoneCode.KEY_OLD_PHONE.getValue());
			}else if(session.getAttribute(PhoneCode.KEY_NEW_PHONE.getValue()) != null){//4绑定新手机
				code = (String)session.getAttribute(PhoneCode.KEY_NEW_PHONE.getValue());
			}*/
            String code = (String) session.getAttribute(PhoneCode.KEY_REGIST.getValue());
            if(code==null||!code.equals(param.getCode())){
                log.error("验证码不同:1."+code+" 2."+param.getCode());
                return toError("验证码错误！");
            }
            String phone=(String) session.getAttribute("phone");
            if(!phone.equals(param.getPhone())){
                log.error("手机号不同");
                return toError("验证码错误！");
            }
            return toSuccess("验证验证码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
}
