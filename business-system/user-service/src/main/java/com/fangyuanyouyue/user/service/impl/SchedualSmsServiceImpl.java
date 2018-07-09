package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.service.SchedualSmsService;
import org.springframework.stereotype.Component;

@Component
public class SchedualSmsServiceImpl implements SchedualSmsService{

    @Override
    public String sendCode(String phone, Integer typet) {
        return "系统繁忙，请稍后重试！";
    }
}
