package com.fangyuanyouyue.user.service;

import com.fangyuanyouyue.user.service.impl.SchedualSmsServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sms-service",fallback = SchedualSmsServiceImpl.class)
@Component
public interface SchedualSmsService {
    @RequestMapping(value = "/sms/sendCode",method = RequestMethod.POST)
    String sendCode(@RequestParam(value = "phone") String phone, @RequestParam(value = "type") Integer typet);
}
