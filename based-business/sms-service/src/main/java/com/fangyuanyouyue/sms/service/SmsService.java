package com.fangyuanyouyue.sms.service;

public interface SmsService {
    //发送验证码
    public void sendCode(String phone, String code,String ip) throws Exception;
}
