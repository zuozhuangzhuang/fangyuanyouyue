package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseClientResult;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.model.UserInfo;
import com.fangyuanyouyue.user.param.UserParam;
import com.fangyuanyouyue.user.service.*;
import com.fangyuanyouyue.user.utils.ServiceException;
import com.fangyuanyouyue.user.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping(value = "/userFeign")
@Api(description = "用户系统外部调用Controller")
@RefreshScope
public class FeignController  extends BaseController {

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


    @ApiOperation(value = "验证用户", notes = "验证用户",position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/verifyUser")
    @ResponseBody
    public String verifyUser(Integer userId) throws IOException {
        try {
            log.info("----》验证用户《----");
            if(userId == null){
                return toError("用户ID不能为空！");
            }
            UserInfo userInfo=userInfoService.selectByPrimaryKey(userId);
            if(userInfo==null){
                return toError("999","登录超时，请重新登录！");
            }
            if(userInfo.getStatus() == 2){
                return toError("999","您的账号已被冻结，请联系管理员！");
            }
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "验证用户成功！");
            result.put("userInfo",userInfo);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
}
