package com.fangyuanyouyue.message.controller;

import com.fangyuanyouyue.message.client.BaseController;
import com.fangyuanyouyue.message.param.MessageParam;
import com.fangyuanyouyue.message.service.MessageService;
import com.fangyuanyouyue.message.utils.CheckCode;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/message")
@Api(description = "商品系统Controller")
@RefreshScope
public class MessageController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/sendCode")
    @ResponseBody
    public String sendCode(MessageParam param) throws IOException {
        try {
            log.info("----》发送验证码《----");
            log.info("参数："+param.toString());

            String code = CheckCode.getCheckCode();
            messageService.sendCode(param.getPhone(), code, "");

            return toSuccess(code,"发送验证码成功");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

}
