package com.fangyuanyouyue.goods.controller;

import com.fangyuanyouyue.goods.client.BaseClientResult;
import com.fangyuanyouyue.goods.client.BaseController;
import com.fangyuanyouyue.goods.model.Goods;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.GoodsService;
import com.fangyuanyouyue.goods.utils.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@RestController
@RequestMapping(value = "/goods")
@Api(description = "商品系统Controller")
@RefreshScope
public class GoodsController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private GoodsService goodsService;
//    @Value("${name}")
//    String name;
//    @Value("${version}")
//    String version;

//    @RequestMapping("/hi")
//    @ResponseBody
//    public String hi() {
//        return "hi,I am " + name;
//    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "用户token", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "classify", value = "商品类型0默认  1经济类 2中档类 3精品类 4喜好推荐 5欣赏类 6杂件类", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "限制页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/goodsList")
    @ResponseBody
    public String goodsList(GoodsParam param) throws IOException {
        try {
            log.info("----》获取商品列表《----");
            log.info("参数：" + param.toString());
            //TODO 获取商品列表

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取商品列表成功！");
            List<Goods> goodsList = goodsService.getGoodsList(param.getStart(),param.getLimit());
            result.put("goodsList",goodsList);
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "catalogId", value = "商品类型", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "商品价格", required = true, dataType = "decimal", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "商品图片路径（6长图片）file1~file6", required = true, dataType = "file", paramType = "query"),
            @ApiImplicitParam(name = "imgWidth", value = "图片宽度", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "imgHeight", value = "图片高度", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "商品描述(详情)", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "isSpecial", value = "是否特价商品 0是  1否", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "postage", value = "邮费", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "区分商品是新分类还是旧分类，1是新分类", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/addGoods")
    @ResponseBody
    public String addGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》发布商品《----");
            log.info("参数："+param.toString());
            /*验证发布人信息*/
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("商品类型不能为空！");
            }
            if(StringUtils.isEmpty(param.getTitle())){
                return toError("商品标题不能为空！");
            }
            if(param.getPrice()==null){
                return toError("商品价格不能为空！");
            }
            if(StringUtils.isEmpty(param.getDescription())){
                return toError("商品描述不能为空！");
            }
            //			if(StringUtils.isEmpty(param.getIsSpecial())){
            //				return toError("请选择商品是否为特价商品！");
            //			}
            if(StringUtils.isEmpty(param.getIsSpecial())){
                param.setIsSpecial("1");
            }
            //判断用户信息
            //TODO 添加商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "添加商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "删除商品", notes = "删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "goods", value = "商品id(多个商品id,号拼接)", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "编辑类型 0 普通商品  1拍卖商品", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/deleteGoods")
    @ResponseBody
    public String deleteGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》批量删除商品《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(StringUtils.isEmpty(param.getType())){
                return toError("商品类型不能为空！");
            }
//            User user=userService.getByToken(param.getToken());
//            if(user==null){
//                return toError("999","登录超时，请重新登录！");
//            }
//            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
//                return toError("999","您的账号已被冻结，请联系管理员！");
//            }
            if(StringUtils.isEmpty(param.getGoodsIds())){
                return toError("商品ID不能为空！");
            }
            //TODO 批量删除商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "批量删除商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //同类推荐
    @ApiOperation(value = "同类推荐", notes = "同类推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId ", value = "商品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "catalogId", value = "推荐类型", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "区分商品是新分类还是旧分类，1是新分类", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/similarGoods")
    @ResponseBody
    public String similarGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》同类推荐《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 同类推荐

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "同类推荐成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //查询商品

    //我的商品
    @ApiOperation(value = "我的商品", notes = "我的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/userGoods")
    @ResponseBody
    public String userGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》我的商品《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 我的商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //我拍下的商品
    @ApiOperation(value = "我拍下的商品", notes = "我拍下的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/buyGoods")
    @ResponseBody
    public String buyGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》我拍下的商品《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 我拍下的商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我拍下的商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //我卖出的商品
    @ApiOperation(value = "我卖出的商品", notes = "我卖出的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/sellGoods")
    @ResponseBody
    public String sellGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》我卖出的商品《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 我卖出的商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我卖出的商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
    //我的收藏
    @ApiOperation(value = "我的收藏", notes = "我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/myCollect")
    @ResponseBody
    public String myCollect(GoodsParam param) throws IOException {
        try {
            log.info("----》我的收藏《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 我的收藏

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的收藏成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
    //他的商品
    @ApiOperation(value = "他的商品", notes = "他的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/otherGoods")
    @ResponseBody
    public String otherGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》他的商品《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 他的商品

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取他的商品成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
    //商品鉴定申请
    @ApiOperation(value = "商品鉴定申请", notes = "商品鉴定申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "file", value = "商品图片（6张图片）file1~file6", dataType = "file", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "分页limit", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
    })
    @PostMapping(value = "/authenticate")
    @ResponseBody
    public String authenticate(GoodsParam param) throws IOException {
        try {
            log.info("----》商品鉴定申请《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(StringUtils.isEmpty(param.getTitle())){
                return toError("商品标题不能为空！");
            }
//            AUser user=userService.getByToken(param.getToken());
//            if(user==null){
//                return toError("999","登录超时，请重新登录！");
//            }
//            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
//                return toError("999","您的账号已被冻结，请联系管理员！");
//            }
            if(StringUtils.isEmpty(param.getContent())){
                return toError("商品介绍不能为空!");
            }
            //TODO 商品鉴定申请

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "商品鉴定申请成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }
    //我的鉴定列表
    @ApiOperation(value = "我的鉴定列表", notes = "我的鉴定列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/authenticateList")
    @ResponseBody
    public String authenticateList(GoodsParam param) throws IOException {
        try {
            log.info("----》我的鉴定列表《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError("商品id不能为空！");
            }
            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
                return toError("推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError("start不能为空！");
            }
            if(param.getLimit()==null){
                return toError("limit不能为空！");
            }
            //TODO 我的鉴定列表

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的鉴定列表成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //收藏商品/取消收藏
    @ApiOperation(value = "收藏商品/取消收藏 ", notes = "收藏商品/取消收藏 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "收藏类型 0收藏商品   1取消收藏", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true,  dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/collectGoods")
    @ResponseBody
    public String collectGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》收藏商品/取消收藏《----");
            log.info("参数："+param.toString());
            if(StringUtils.isEmpty(param.getToken())){
                return toError("用户token不能为空！");
            }
            if(param.getGoodsId()==null){
                return toError("商品id不能为空！");
            }
            if(StringUtils.isEmpty(param.getType())){
                return toError("收藏类型不能为空！");
            }
//            AUser user=userService.getByToken(param.getToken());
//            if(user==null){
//                return toError("999","登录超时，请重新登录！");
//            }
//            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
//                return toError("999","您的账号已被冻结，请联系管理员！");
//            }
//            AGoods goods=goodsService.get(param.getGoodsId());
//            if(goods==null){
//                return toError("此商品不存在,参数有误！");
//            }
            //TODO 收藏商品/取消收藏

            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "收藏商品/取消收藏成功！");
            return toResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

}
