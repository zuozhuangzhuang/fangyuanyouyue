package com.fangyuanyouyue.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.client.BaseController;
import com.fangyuanyouyue.goods.dto.GoodsCategoryDto;
import com.fangyuanyouyue.goods.dto.GoodsDto;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.GoodsInfoService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/goods")
@Api(description = "商品系统Controller")
@RefreshScope
public class GoodsController extends BaseController{
    protected Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用


    @ApiOperation(value = "获取商品列表", notes = "获取商品列表",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "起始页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "限制页", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/goodsList")
    @ResponseBody
    public String goodsList(GoodsParam param) throws IOException {
        try {
            log.info("----》获取商品列表《----");
            log.info("参数：" + param.toString());
            if(param.getStart() == null){
                return toError("起始页不能为空！");
            }
            if(param.getLimit() == null){
                return toError("限制页不能为空！");
            }
            //TODO 获取商品列表
            List<GoodsDto> goodsDtos = goodsInfoService.getGoodsInfoList(param.getStart(),param.getLimit());
            return toSuccess(goodsDtos,"获取商品列表成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    @ApiOperation(value = "添加商品", notes = "添加商品",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId ", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsInfoName", value = "商品名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goodsCategoryIds", value = "商品分类数组（同一商品可多种分类）", required = true,allowMultiple = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "商品描述(详情)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "price", value = "商品价格", required = true, dataType = "BigDecimal", paramType = "query"),
            @ApiImplicitParam(name = "postage", value = "运费", required = true, dataType = "BigDecimal", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "标签", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1普通商品 2秒杀商品", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 普通商品 1出售中 2 已售出 5删除", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "file1", value = "file1", required = true, dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "file2", value = "file2", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "file3", value = "file3", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "file4", value = "file4", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "file5", value = "file5", dataType = "file", paramType = "form"),
            @ApiImplicitParam(name = "file6", value = "file6", dataType = "file", paramType = "form")
    })
    @PostMapping(value = "/addGoods")
    @ResponseBody
    public String addGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》发布商品《----");
            log.info("参数："+param.toString());
            //验证用户
            if(param.getUserId() == null){
                return toError("用户id不能为空！");
            }
            String verifyUser = schedualUserService.verifyUserById(param.getUserId());
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            JSONObject user = JSONObject.parseObject(jsonObject.getString("userInfo"));
            if(user==null){
                return toError("登录超时，请重新登录！");
            }
            if((int)user.get("status") == 2){
                return toError("您的账号已被冻结，请联系管理员！");
            }
            if(StringUtils.isEmpty(param.getGoodsInfoName())){
                return toError("商品名称不能为空！");
            }
            if(param.getGoodsCategoryIds().length<1){
                return toError("商品分类不能为空！");
            }
            if(StringUtils.isEmpty(param.getDescription())){
                return toError("商品详情不能为空！");
            }
            if(param.getPrice()==null){
                return toError("商品价格不能为空！");
            }
            if(param.getPostage() == null){
                return  toError("商品运费不能为空！");
            }
            if(param.getType() == null){
                return  toError("商品类型不能为空！");
            }
            if(param.getFile1() == null){
                return toError("请至少上传一张图片！");
            }
            //TODO 添加商品,返回值应当包含商品图片信息，这里只有商品信息
            GoodsDto goodsDto = goodsInfoService.addGoods(param.getUserId(),user.getString("nickName"),param);
            return toSuccess(goodsDto,"添加商品成功！");
        } catch (ServiceException e) {
            e.printStackTrace();
            return toError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }


    @ApiOperation(value = "批量删除商品", notes = "批量删除商品",response = ResultUtil.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId ", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsInfoIds", value = "商品id数组", required = true, allowMultiple = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/deleteGoods")
    @ResponseBody
    public String deleteGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》批量删除商品《----");
            log.info("参数："+param.toString());
            //验证用户
            if(param.getUserId() == null){
                return toError("用户id不能为空！");
            }
            String verifyUser = schedualUserService.verifyUserById(param.getUserId());
            JSONObject jsonObject = JSONObject.parseObject(verifyUser);
            JSONObject user = JSONObject.parseObject(jsonObject.getString("userInfo"));
            if(user==null){
                return toError("登录超时，请重新登录！");
            }
            if((int)user.get("status") == 2){
                return toError("您的账号已被冻结，请联系管理员！");
            }
            if(param.getGoodsInfoIds().length<1){
                return toError("商品ID不能为空！");
            }
            for(Integer goodsId:param.getGoodsInfoIds()){
                //TODO 依次查询商品是否有未完成订单，有订单则不能删
                GoodsInfo goodsInfo = goodsInfoService.selectByPrimaryKey(goodsId);
                if(false){

                    return toError("商品"+goodsInfo.getName()+"存在未完成订单，请勿删除！");
                }
            }
            //TODO 批量删除商品
            goodsInfoService.deleteGoods(param.getGoodsInfoIds());
            return toSuccess("批量删除商品成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //分类列表
    @ApiOperation(value = "获取分类列表", notes = "获取分类列表")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/categoryList")
    @ResponseBody
    public String categoryList(GoodsParam param) throws IOException {
        try {
            log.info("----》获取分类列表《----");
            log.info("参数："+param.toString());
            //TODO 同类推荐
            List<GoodsCategoryDto> categoryDtos = goodsInfoService.categoryList();
            return toSuccess(categoryDtos,"获取分类列表成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

    //同类推荐
    @ApiOperation(value = "同类推荐", notes = "同类推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId ", value = "商品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping(value = "/similarGoods")
    @ResponseBody
    public String similarGoods(GoodsParam param) throws IOException {
        try {
            log.info("----》同类推荐《----");
            log.info("参数："+param.toString());

            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
                return toError(1,"商品id不能为空！");
            }
            if(param.getGoodsInfoIds().length<1){
                return toError(1,"推荐类型不能为空！");
            }
            if(param.getStart()==null){
                return toError(1,"start不能为空！");
            }
            if(param.getLimit()==null){
                return toError(1,"limit不能为空！");
            }
            //TODO 同类推荐
            List<GoodsDto> goodsDtos = new ArrayList<>();

            return toSuccess(goodsDtos,"同类推荐成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return toError("系统繁忙，请稍后再试！");
        }
    }

//    //查询商品
//
//    //我的商品
//    @ApiOperation(value = "我的商品", notes = "我的商品")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/userGoods")
//    @ResponseBody
//    public String userGoods(GoodsParam param) throws IOException {
//        try {
//            log.info("----》我的商品《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 我的商品
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的商品成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    //我拍下的商品
//    @ApiOperation(value = "我拍下的商品", notes = "我拍下的商品")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/buyGoods")
//    @ResponseBody
//    public String buyGoods(GoodsParam param) throws IOException {
//        try {
//            log.info("----》我拍下的商品《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 我拍下的商品
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我拍下的商品成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    //我卖出的商品
//    @ApiOperation(value = "我卖出的商品", notes = "我卖出的商品")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/sellGoods")
//    @ResponseBody
//    public String sellGoods(GoodsParam param) throws IOException {
//        try {
//            log.info("----》我卖出的商品《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 我卖出的商品
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我卖出的商品成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//    //我的收藏
//    @ApiOperation(value = "我的收藏", notes = "我的收藏")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/myCollect")
//    @ResponseBody
//    public String myCollect(GoodsParam param) throws IOException {
//        try {
//            log.info("----》我的收藏《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 我的收藏
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的收藏成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//    //他的商品
//    @ApiOperation(value = "他的商品", notes = "他的商品")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "search", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/otherGoods")
//    @ResponseBody
//    public String otherGoods(GoodsParam param) throws IOException {
//        try {
//            log.info("----》他的商品《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 他的商品
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取他的商品成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//    //商品鉴定申请
//    @ApiOperation(value = "商品鉴定申请", notes = "商品鉴定申请")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "file", value = "商品图片（6张图片）file1~file6", dataType = "file", paramType = "query"),
//            @ApiImplicitParam(name = "content", value = "分页limit", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "title", value = "搜索内容（标题和内容）", dataType = "string", paramType = "query")
//    })
//    @PostMapping(value = "/authenticate")
//    @ResponseBody
//    public String authenticate(GoodsParam param) throws IOException {
//        try {
//            log.info("----》商品鉴定申请《----");
//            log.info("参数："+param.toString());
//            if(StringUtils.isEmpty(param.getToken())){
//                return toError("用户token不能为空！");
//            }
//            if(StringUtils.isEmpty(param.getTitle())){
//                return toError("商品标题不能为空！");
//            }
////            AUser user=userService.getByToken(param.getToken());
////            if(user==null){
////                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
////            }
////            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
////                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
////            }
//            if(StringUtils.isEmpty(param.getContent())){
//                return toError("商品介绍不能为空!");
//            }
//            //TODO 商品鉴定申请
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "商品鉴定申请成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//    //我的鉴定列表
//    @ApiOperation(value = "我的鉴定列表", notes = "我的鉴定列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "start", value = "分页start", required = true, dataType = "int", paramType = "query"),
//            @ApiImplicitParam(name = "limit", value = "分页limit", required = true, dataType = "int", paramType = "query")
//    })
//    @PostMapping(value = "/authenticateList")
//    @ResponseBody
//    public String authenticateList(GoodsParam param) throws IOException {
//        try {
//            log.info("----》我的鉴定列表《----");
//            log.info("参数："+param.toString());
//
//            if(param.getGoodsId()==null || param.getGoodsId().intValue()==0){
//                return toError("商品id不能为空！");
//            }
//            if(param.getCatalogId()==null || param.getCatalogId().intValue()==0){
//                return toError("推荐类型不能为空！");
//            }
//            if(param.getStart()==null){
//                return toError("start不能为空！");
//            }
//            if(param.getLimit()==null){
//                return toError("limit不能为空！");
//            }
//            //TODO 我的鉴定列表
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "获取我的鉴定列表成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }
//
//    //收藏商品/取消收藏
//    @ApiOperation(value = "收藏商品/取消收藏 ", notes = "收藏商品/取消收藏 ")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token  ", value = "用户token", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "type", value = "收藏类型 0收藏商品   1取消收藏", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true,  dataType = "int", paramType = "query")
//    })
//    @PostMapping(value = "/collectGoods")
//    @ResponseBody
//    public String collectGoods(GoodsParam param) throws IOException {
//        try {
//            log.info("----》收藏商品/取消收藏《----");
//            log.info("参数："+param.toString());
//            if(StringUtils.isEmpty(param.getToken())){
//                return toError("用户token不能为空！");
//            }
//            if(param.getGoodsId()==null){
//                return toError("商品id不能为空！");
//            }
//            if(StringUtils.isEmpty(param.getType())){
//                return toError("收藏类型不能为空！");
//            }
//
////            ResponseEntity<List> responseEntity = restTemplate.postForObject("http://localhost/user/login");
//
////            AUser user=userService.getByToken(param.getToken());
////            if(user==null){
////                return toError(ReCode.FAILD.getValue(),"登录超时，请重新登录！");
////            }
////            if(StringUtils.isNotEmpty(user.getStatus()) && "1".equals(user.getStatus())){
////                return toError(ReCode.FAILD.getValue(),"您的账号已被冻结，请联系管理员！");
////            }
////            AGoods goods=goodsService.get(param.getGoodsId());
////            if(goods==null){
////                return toError("此商品不存在,参数有误！");
////            }
//            //TODO 收藏商品/取消收藏
//
//            BaseClientResult result = new BaseClientResult(Status.YES.getValue(), "收藏商品/取消收藏成功！");
//            return toResult(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return toError("系统繁忙，请稍后再试！");
//        }
//    }

}
