package com.fangyuanyouyue.goods.model;

import java.util.Date;
/*
private Integer userId;//发布用户id

    private String name;//商品名称

    private String description;//商品详情

    private BigDecimal price;//商品价格

    private BigDecimal postage;//运费

    private Integer sort;//排序

    private String label;//标签

    private Integer type;//类型 1普通商品 2秒杀商品

    private Integer status;//状态

    private Date addTime;//添加时间

    private Date updateTime;//更新时间

    private Integer catalogid;//商品分类ID

    List<GoodsInfo> getGoodsList(int pageNum, int pageSize);

    <select id="getGoodsList" parameterType="Integer" resultMap="BaseResultMap">
    select * from goods_info
  </select>
 */
public class GoodsImg {
    private Integer id;

    private Integer goodsId;//商品id

    private String imgUrl;//图片地址

    private Integer type;//类型 1主图（展示在第一张的图片） 2次图

    private Integer sort;//排序

    private Date addTime;//添加时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}