package com.fangyuanyouyue.order.service;

import com.fangyuanyouyue.order.service.impl.SchedualGoodsServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "goods-service",fallback = SchedualGoodsServiceImpl.class)
@Component
public interface SchedualGoodsService {
    @RequestMapping(value = "/goods/goodsInfo",method = RequestMethod.GET)
    String goodsInfo(@RequestParam(value = "goodsId") Integer goodsId);
    @RequestMapping(value = "/goodsFeign/updateGoodsStatus",method = RequestMethod.POST)
    String updateGoodsStatus(@RequestParam(value = "goodsId") Integer goodsId,@RequestParam(value = "status") Integer status);
}
