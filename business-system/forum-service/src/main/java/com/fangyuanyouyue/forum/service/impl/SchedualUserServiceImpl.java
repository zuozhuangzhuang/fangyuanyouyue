package com.fangyuanyouyue.forum.service.impl;

import com.fangyuanyouyue.forum.service.SchedualUserService;
import org.springframework.stereotype.Component;

@Component
public class SchedualUserServiceImpl implements SchedualUserService{
    @Override
    public String verifyUserById(Integer userId) {
        return "系统繁忙，请稍后重试！";
    }


    @Override
    public String verifyUserByPhone(String phone) {
        return "系统繁忙，请稍后重试！";
    }

    @Override
    public String verifyUserByUnionId(String unionId, Integer type) {
        return "系统繁忙，请稍后重试！";
    }
}
