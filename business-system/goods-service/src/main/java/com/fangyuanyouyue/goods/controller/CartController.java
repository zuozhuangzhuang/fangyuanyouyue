package com.fangyuanyouyue.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.client.BaseController;
import com.fangyuanyouyue.goods.dto.CartShopDto;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.CartService;
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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/cart")
@Api(description = "购物车系统Controller")
@RefreshScope
public class CartController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用
    @Autowired
    protected RedisTemplate redisTemplate;
    @Autowired
    private CartService cartService;


    //TODO 购物车：1.添加商品到购物车 2.查看购物车内商品列表 3.根据店铺区分购物车内商品 4.删除购物车里的商品
    //添加商品到购物车
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/addGoodsToCart")
    @ResponseBody
    public String addGoodsToCart(GoodsParam param) throws IOException {
        try {
            log.info("----》添加商品到购物车《----");
            //验证用户
            if(StringUtils.isEmpty(param.getToken())){
                return toError(ReCode.FAILD.getValue(),"用户token不能为空！");
            }
            Integer userId = (Integer)redisTemplate.opsForValue().get(param.getToken());
            String verifyUser = schedualUserService.verifyUserById(userId);
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            if((Integer)jsonObject.get("code") != 0){
                return toError(jsonObject.getString("report"));
            }

            //TODO 添加商品到购物车 返回购物车内商品列表
            cartService.addGoodsToCart(userId, param.getGoodsId());
            return toSuccess( "添加商品到购物车成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }

    //我的购物车
    @ApiOperation(value = "我的购物车", notes = "我的购物车",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/getCart")
    @ResponseBody
    public String getCart(GoodsParam param) throws IOException{
        try {
            log.info("----》我的购物车《----");
            //验证用户
            if(StringUtils.isEmpty(param.getToken())){
                return toError(ReCode.FAILD.getValue(),"用户token不能为空！");
            }
            Integer userId = (Integer)redisTemplate.opsForValue().get(param.getToken());
            String verifyUser = schedualUserService.verifyUserById(userId);
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            if((Integer)jsonObject.get("code") != 0){
                return toError(jsonObject.getString("report"));
            }
            redisTemplate.expire(param.getToken(),7, TimeUnit.DAYS);
            //TODO 我的购物车
            List<CartShopDto> cart = cartService.getCart(userId);
            return toSuccess( cart,"获取我的购物车成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }


    //移出购物车
    @ApiOperation(value = "移出购物车", notes = "移出购物车",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cartDetailIds", value = "购物车详情ID数组",allowMultiple = true,  required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/cartRemove")
    @ResponseBody
    public String cartRemove(GoodsParam param) throws IOException{
        try {
            log.info("----》移出购物车《----");
            //验证用户
            if(StringUtils.isEmpty(param.getToken())){
                return toError(ReCode.FAILD.getValue(),"用户token不能为空！");
            }
            Integer userId = (Integer)redisTemplate.opsForValue().get(param.getToken());
            String verifyUser = schedualUserService.verifyUserById(userId);
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            if((Integer)jsonObject.get("code") != 0){
                return toError(jsonObject.getString("report"));
            }
            redisTemplate.expire(param.getToken(),7, TimeUnit.DAYS);
            if(param.getCartDetailIds() == null || param.getCartDetailIds().length == 0){
                return toError(ReCode.FAILD.getValue(),"购物车详情id不能为空！");
            }
            //TODO 移出购物车
            cartService.cartRemove(param.getCartDetailIds());
            return toSuccess( "移出购物车成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError(ReCode.FAILD.getValue(),"系统繁忙，请稍后再试！");
        }
    }


}
