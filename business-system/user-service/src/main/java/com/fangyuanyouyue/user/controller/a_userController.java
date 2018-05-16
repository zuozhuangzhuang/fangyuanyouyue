package com.fangyuanyouyue.user.controller;

import com.fangyuanyouyue.user.client.BaseClientResult;
import com.fangyuanyouyue.user.client.BaseController;
import com.fangyuanyouyue.user.model.User;
import com.fangyuanyouyue.user.model.a_user;
import com.fangyuanyouyue.user.param.BaseParam;
import com.fangyuanyouyue.user.user.a_userService;
import com.fangyuanyouyue.user.utils.Status;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class a_userController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private a_userService a_userService;

    @ApiOperation(value="获取用户列表", notes="获取用户列表")
    @ResponseBody
    @GetMapping(value="/getList")
    public String getList(BaseParam param) throws IOException {
        try{
            log.info("----》测试获取结果《----");
            log.info("参数："+param.toString());
            List<a_user> a_users = a_userService.getList();
            BaseClientResult result = new BaseClientResult(Status.YES.getValue(),"请求成功！");
            result.put("a_users",a_users);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
}
