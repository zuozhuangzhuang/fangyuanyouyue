package com.fangyuanyouyue.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.client.BaseController;
import com.fangyuanyouyue.goods.dto.GoodsDto;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.GoodsInfoService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
import com.fangyuanyouyue.goods.utils.ReCode;
import com.fangyuanyouyue.goods.utils.ResultUtil;
import com.fangyuanyouyue.goods.utils.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/goodsFeign")
@Api(description = "商品系统外部调用Controller")
@RefreshScope
public class FeignController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用
    @Autowired
    protected RedisTemplate redisTemplate;

    @PostMapping(value = "/updateGoodsStatus")
    @ResponseBody
    public String updateGoodsStatus(GoodsParam param) throws IOException {
        try {
            log.info("----》修改商品状态《----");
            log.info("参数：" + param.toString());
            if(param.getGoodsId() == null){
                return toError(ReCode.FAILD.getValue(),"商品ID不能为空！");
            }
            if(param.getStatus() == null){
                return toError(ReCode.FAILD.getValue(),"状态不能为空！");
            }
            //修改商品状态
            goodsInfoService.updateGoodsStatus(param.getGoodsId(),param.getStatus());
            return toSuccess("修改商品状态成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }
}
