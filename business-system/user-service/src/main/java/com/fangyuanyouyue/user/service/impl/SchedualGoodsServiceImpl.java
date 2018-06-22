package com.fangyuanyouyue.user.service.impl;

import com.fangyuanyouyue.user.service.SchedualGoodsService;
import org.springframework.stereotype.Component;

@Component
public class SchedualGoodsServiceImpl implements SchedualGoodsService{
    @Override
    public String goodsList(String classify, String start, String limit) {
        return "系统繁忙，请稍后重试！";
    }
}
