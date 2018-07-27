package com.fangyuanyouyue.goods.controller;

import com.fangyuanyouyue.goods.client.BaseController;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.GoodsInfoService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
import com.fangyuanyouyue.goods.utils.ReCode;
import com.fangyuanyouyue.goods.utils.ServiceException;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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


    @GetMapping(value = "/goodsMainImg")
    @ResponseBody
    public String goodsMainImg(GoodsParam param) throws IOException {
        try {
            log.info("----》获取商品主图《----");
            log.info("参数：" + param.toString());

            if (param.getGoodsId() == null) {
                return toError(ReCode.FAILD.getValue(), "商品id不能为空！");
            }
            //商品详情
            String goodsMainImg = goodsInfoService.goodsMainImg(param.getGoodsId());

            return toSuccess(goodsMainImg, "获取商品主图成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }
    @GetMapping(value = "/goodsInfo")
    @ResponseBody
    public String goodsInfo(GoodsParam param) throws IOException {
        try {
            log.info("----》商品详情《----");
            log.info("参数：" + param.toString());

            if (param.getGoodsId() == null) {
                return toError(ReCode.FAILD.getValue(), "商品id不能为空！");
            }
            //商品详情
            GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(param.getGoodsId());

            return toSuccess(goodsInfo, "商品详情成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }

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
