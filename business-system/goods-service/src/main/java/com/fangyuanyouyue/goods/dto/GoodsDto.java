package com.fangyuanyouyue.goods.dto;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.model.GoodsComment;
import com.fangyuanyouyue.goods.model.GoodsCorrelation;
import com.fangyuanyouyue.goods.model.GoodsImg;
import com.fangyuanyouyue.goods.model.GoodsInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品信息Dto
 */
public class GoodsDto {
    //GoodsInfo
    private Integer goodsId;//商品ID

    private Integer userId;//发布用户id

    private String nickName;//昵称

    private String headImgUrl;//头像图片地址

    private String name;//商品名称

    private String description;//商品详情

    private BigDecimal price;//商品价格

    private BigDecimal postage;//运费

    private Integer sort;//排序

    private String label;//标签

    private Integer status;//状态 普通商品 1出售中 2已售出 5删除

    private BigDecimal floorPrice;//最低价

    private Date intervalTime;//降价时间间隔

    private BigDecimal markdown;//降价幅度

    //GoodsImg
    private List<GoodsImgDto> goodsImgDtos;//商品图片

    private String mainUrl;//商品主图

    //GoodsCorrelation
    private List<GoodsCorrelationDto> goodsCorrelations;//商品分类

    //GoodsComment
    private List<GoodsCommentDto> goodsCommentDtos;//商品评论列表

    //公用
    private Integer type;//类型 1普通商品 2秒杀商品 /1主图（展示在第一张的图片） 2次图

    private String userAddress;//店家地址

    public GoodsDto() {

    }

    public GoodsDto(JSONObject user,GoodsInfo goodsInfo, List<GoodsImg> goodsImgs, List<GoodsCorrelation> goodsCorrelations, List<GoodsComment> goodsComments) {
        if(user != null){
            this.nickName = user.getString("nickName");
            this.headImgUrl = user.getString("headImgUrl");
            this.userAddress = user.getString("address");
        }
        if(goodsInfo != null){
            //GoodsInfo
            this.goodsId = goodsInfo.getId();
            this.userId = goodsInfo.getUserId();
            this.name = goodsInfo.getName();
            this.description = goodsInfo.getDescription();
            this.price = goodsInfo.getPrice();
            this.postage = goodsInfo.getPostage();
            this.sort = goodsInfo.getSort();
            this.label = goodsInfo.getLabel();
            this.type = goodsInfo.getType();
            this.status = goodsInfo.getStatus();
            this.floorPrice = goodsInfo.getFloorPrice();
            this.intervalTime = goodsInfo.getIntervalTime();
            this.markdown = goodsInfo.getMarkdown();
        }
        //GoodsImg
        if(goodsImgs != null && goodsImgs.size()>0){
            List<GoodsImgDto> goodsImgDtos = GoodsImgDto.toDtoList(goodsImgs);
            this.goodsImgDtos = goodsImgDtos;
            for(GoodsImgDto goodsImgDto:goodsImgDtos){
                if(goodsImgDto.getSort() == 1){
                    this.mainUrl = goodsImgDto.getImgUrl();
                }
            }
        }
        if(goodsCorrelations != null && goodsCorrelations.size()>0){
            //GoodsCorrelation
            this.goodsCorrelations = GoodsCorrelationDto.toDtoList(goodsCorrelations);
        }

        //GoodsComment
        if(goodsComments != null && goodsComments.size()>0){
            this.goodsCommentDtos = GoodsCommentDto.toDtoList(goodsComments);
        }
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<GoodsImgDto> getGoodsImgDtos() {
        return goodsImgDtos;
    }

    public void setGoodsImgDtos(List<GoodsImgDto> goodsImgDtos) {
        this.goodsImgDtos = goodsImgDtos;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public List<GoodsCorrelationDto> getGoodsCorrelations() {
        return goodsCorrelations;
    }

    public void setGoodsCorrelations(List<GoodsCorrelationDto> goodsCorrelations) {
        this.goodsCorrelations = goodsCorrelations;
    }

    public List<GoodsCommentDto> getGoodsCommentDtos() {
        return goodsCommentDtos;
    }

    public void setGoodsCommentDtos(List<GoodsCommentDto> goodsCommentDtos) {
        this.goodsCommentDtos = goodsCommentDtos;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public BigDecimal getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(BigDecimal floorPrice) {
        this.floorPrice = floorPrice;
    }

    public Date getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Date intervalTime) {
        this.intervalTime = intervalTime;
    }

    public BigDecimal getMarkdown() {
        return markdown;
    }

    public void setMarkdown(BigDecimal markdown) {
        this.markdown = markdown;
    }
}
