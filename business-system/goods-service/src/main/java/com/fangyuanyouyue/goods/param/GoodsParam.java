package com.fangyuanyouyue.goods.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;

@ApiModel(value = "参数类")
public class GoodsParam{
    //公用
    @ApiModelProperty(name = "start", value = "起始页", dataType = "Integer",hidden = true)
    private Integer start; // 起始页

    @ApiModelProperty(name = "limit", value = "限制页", dataType = "Integer",hidden = true)
    private Integer limit; // 限制页

    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer",hidden = true)
    private Integer type;//类型 goodsInfo：1普通商品 2秒杀商品 goodsCategory：1主图 2次图 goodsCategory：1普通 2热门 bannerIndex：业务类型,0:商品 1：个人

    @ApiModelProperty(name = "goodsId", value = "商品id", dataType = "int",hidden = true)
    private Integer goodsId;//商品id

    @ApiModelProperty(name = "imgUrl", value = "图片地址", dataType = "String",hidden = true)
    private String imgUrl;//图片地址

    @ApiModelProperty(name = "price", value = "价格", dataType = "BigDecimal",hidden = true)
    private BigDecimal price;//价格

    @ApiModelProperty(name = "status", value = "状态", dataType = "int",hidden = true)
    private Integer status;//状态 GoodsComment：1正常 2隐藏 GoodsInfo：普通商品 1出售中 2已售出 5删除


    //GoodsInfo
    @ApiModelProperty(name = "userId", value = "卖家id", dataType = "int",hidden = true)
    private Integer userId;//卖家id

    @ApiModelProperty(name = "token", value = "用户token", dataType = "String",hidden = true)
    private String token;//用户token

    @ApiModelProperty(name = "goodsInfoIds", value = "商品id数组", dataType = "int",hidden = true)
    private Integer[] goodsInfoIds;//商品id数组

    @ApiModelProperty(name = "goodsInfoName", value = "商品名称", dataType = "String",hidden = true)
    private String goodsInfoName;//商品名称

    @ApiModelProperty(name = "description", value = "商品详情", dataType = "String",hidden = true)
    private String description;//商品详情

    @ApiModelProperty(name = "postage", value = "运费", dataType = "BigDecimal",hidden = true)
    private BigDecimal postage;//运费

    @ApiModelProperty(name = "label", value = "标签", dataType = "String",hidden = true)
    private String label;//标签

    @ApiModelProperty(name = "sort", value = "排序", dataType = "int",hidden = true)
    private Integer sort;//排序



    //GoodsImg
    @ApiModelProperty(name = "goodsImgId", value = "商品图片表ID", dataType = "int",hidden = true)
    private Integer goodsImgId;//商品图片表ID

    @ApiModelProperty(name = "file1", value = "图片文件1", dataType = "file",hidden = true)
    private MultipartFile file1;//图片文件1

    @ApiModelProperty(name = "file2", value = "图片文件2", dataType = "file",hidden = true)
    private MultipartFile file2;

    @ApiModelProperty(name = "file3", value = "图片文件3", dataType = "file",hidden = true)
    private MultipartFile file3;

    @ApiModelProperty(name = "file4", value = "图片文件4", dataType = "file",hidden = true)
    private MultipartFile file4;

    @ApiModelProperty(name = "file5", value = "图片文件5", dataType = "file",hidden = true)
    private MultipartFile file5;

    @ApiModelProperty(name = "file6", value = "图片文件6", dataType = "file",hidden = true)
    private MultipartFile file6;



   //GoodsComment
    @ApiModelProperty(name = "goodsCommentId", value = "商品评论表ID", dataType = "int",hidden = true)
    private Integer goodsCommentId;//商品评论表ID

    @ApiModelProperty(name = "commentId", value = "回复评论id", dataType = "int",hidden = true)
    private Integer commentId;//回复评论id

    @ApiModelProperty(name = "content", value = "评论内容", dataType = "String",hidden = true)
    private String content;//评论内容

    @ApiModelProperty(name = "likesCount", value = "点赞次数", dataType = "int",hidden = true)
    private Integer likesCount;//点赞次数

    @ApiModelProperty(name = "img1Url", value = "图片地址1", dataType = "String",hidden = true)
    private String img1Url;//图片地址1

    @ApiModelProperty(name = "img2Url", value = "图片地址2", dataType = "String",hidden = true)
    private String img2Url;//图片地址2

    @ApiModelProperty(name = "img3Url", value = "图片地址3", dataType = "String",hidden = true)
    private String img3Url;//图片地址3



    //GoodsCategory
    @ApiModelProperty(name = "goodsCategoryId", value = "商品类目表ID", dataType = "int",hidden = true)
    private Integer goodsCategoryId;//商品类目表ID

    @ApiModelProperty(name = "goodsCategoryIds", value = "商品分类数组（同一商品可多种分类）", dataType = "int",hidden = true)
    private Integer[] goodsCategoryIds;//商品分类数组（同一商品可多种分类）

    @ApiModelProperty(name = "parentId", value = "上级id", dataType = "int",hidden = true)
    private Integer parentId;//上级id

    @ApiModelProperty(name = "goodsCategoryName", value = "类目名称", dataType = "String",hidden = true)
    private String goodsCategoryName;//类目名称



    //CartInfo
    @ApiModelProperty(name = "cartInfoId", value = "购物车表ID", dataType = "int",hidden = true)
    private Integer cartInfoId;//购物车表ID



    //CartDetail
    @ApiModelProperty(name = "cartDetailId", value = "购物车明细表ID", dataType = "int",hidden = true)
    private Integer cartDetailId;//购物车明细表ID

    @ApiModelProperty(name = "cartId", value = "购物车id", dataType = "int",hidden = true)
    private Integer cartId;//购物车id

    @ApiModelProperty(name = "count", value = "数量", dataType = "int",hidden = true)
    private Integer count;//数量


    //条件查询
    @ApiModelProperty(name = "search", value = "搜索词条", dataType = "String",hidden = true)
    private String search;//搜索词条

    @ApiModelProperty(name = "synthesize", value = "综合", dataType = "int",hidden = true)
    private Integer synthesize;//综合 1：综合排序 2：信用排序 3：价格升序 4：价格降序

    @ApiModelProperty(name = "priceMin", value = "最小价格", dataType = "BigDecimal",hidden = true)
    private BigDecimal priceMin;//最小价格

    @ApiModelProperty(name = "priceMax", value = "最大价格", dataType = "BigDecimal",hidden = true)
    private BigDecimal priceMax;//最大价格

    @ApiModelProperty(name = "quality", value = "品质", dataType = "int",hidden = true)
    private Integer quality;//品质 1：认证店铺 2：官方保真 3：高信誉度

    //轮播图 BannerIndex
    @ApiModelProperty(name = "bannerIndexId", value = "轮播图ID", dataType = "int",hidden = true)
    private Integer bannerIndexId;//轮播图ID

    @ApiModelProperty(name = "businessId", value = "业务ID:商品ID/用户ID", dataType = "int",hidden = true)
    private Integer businessId;//业务ID:商品ID/用户ID

    @ApiModelProperty(name = "jumpType", value = "跳转类型,0:商品 1：个人", dataType = "int",hidden = true)
    private Integer jumpType;//跳转类型,0:商品 1：个人

    @ApiModelProperty(name = "title", value = "描述标题", dataType = "String",hidden = true)
    private String title;//描述标题

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer[] getGoodsInfoIds() {
        return goodsInfoIds;
    }

    public void setGoodsInfoIds(Integer[] goodsInfoIds) {
        this.goodsInfoIds = goodsInfoIds;
    }


    public String getGoodsInfoName() {
        return goodsInfoName;
    }

    public void setGoodsInfoName(String goodsInfoName) {
        this.goodsInfoName = goodsInfoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getGoodsImgId() {
        return goodsImgId;
    }

    public void setGoodsImgId(Integer goodsImgId) {
        this.goodsImgId = goodsImgId;
    }

    public MultipartFile getFile1() {
        return file1;
    }

    public void setFile1(MultipartFile file1) {
        this.file1 = file1;
    }

    public MultipartFile getFile2() {
        return file2;
    }

    public void setFile2(MultipartFile file2) {
        this.file2 = file2;
    }

    public MultipartFile getFile3() {
        return file3;
    }

    public void setFile3(MultipartFile file3) {
        this.file3 = file3;
    }

    public MultipartFile getFile4() {
        return file4;
    }

    public void setFile4(MultipartFile file4) {
        this.file4 = file4;
    }

    public MultipartFile getFile5() {
        return file5;
    }

    public void setFile5(MultipartFile file5) {
        this.file5 = file5;
    }

    public MultipartFile getFile6() {
        return file6;
    }

    public void setFile6(MultipartFile file6) {
        this.file6 = file6;
    }

    public Integer getGoodsCommentId() {
        return goodsCommentId;
    }

    public void setGoodsCommentId(Integer goodsCommentId) {
        this.goodsCommentId = goodsCommentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public String getImg1Url() {
        return img1Url;
    }

    public void setImg1Url(String img1Url) {
        this.img1Url = img1Url;
    }

    public String getImg2Url() {
        return img2Url;
    }

    public void setImg2Url(String img2Url) {
        this.img2Url = img2Url;
    }

    public String getImg3Url() {
        return img3Url;
    }

    public void setImg3Url(String img3Url) {
        this.img3Url = img3Url;
    }

    public Integer getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public Integer[] getGoodsCategoryIds() {
        return goodsCategoryIds;
    }

    public void setGoodsCategoryIds(Integer[] goodsCategoryIds) {
        this.goodsCategoryIds = goodsCategoryIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getGoodsCategoryName() {
        return goodsCategoryName;
    }

    public void setGoodsCategoryName(String goodsCategoryName) {
        this.goodsCategoryName = goodsCategoryName;
    }

    public Integer getCartInfoId() {
        return cartInfoId;
    }

    public void setCartInfoId(Integer cartInfoId) {
        this.cartInfoId = cartInfoId;
    }

    public Integer getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(Integer cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getSynthesize() {
        return synthesize;
    }

    public void setSynthesize(Integer synthesize) {
        this.synthesize = synthesize;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(BigDecimal priceMax) {
        this.priceMax = priceMax;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBannerIndexId() {
        return bannerIndexId;
    }

    public void setBannerIndexId(Integer bannerIndexId) {
        this.bannerIndexId = bannerIndexId;
    }

    @Override
    public String toString() {
        return "GoodsParam{" +
                "start=" + start +
                ", limit=" + limit +
                ", type=" + type +
                ", goodsId=" + goodsId +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", goodsInfoIds=" + Arrays.toString(goodsInfoIds) +
                ", goodsInfoName='" + goodsInfoName + '\'' +
                ", description='" + description + '\'' +
                ", postage=" + postage +
                ", label='" + label + '\'' +
                ", sort=" + sort +
                ", goodsImgId=" + goodsImgId +
                ", file1=" + file1 +
                ", file2=" + file2 +
                ", file3=" + file3 +
                ", file4=" + file4 +
                ", file5=" + file5 +
                ", file6=" + file6 +
                ", goodsCommentId=" + goodsCommentId +
                ", commentId=" + commentId +
                ", content='" + content + '\'' +
                ", likesCount=" + likesCount +
                ", img1Url='" + img1Url + '\'' +
                ", img2Url='" + img2Url + '\'' +
                ", img3Url='" + img3Url + '\'' +
                ", goodsCategoryId=" + goodsCategoryId +
                ", goodsCategoryIds=" + Arrays.toString(goodsCategoryIds) +
                ", parentId=" + parentId +
                ", goodsCategoryName='" + goodsCategoryName + '\'' +
                ", cartInfoId=" + cartInfoId +
                ", cartDetailId=" + cartDetailId +
                ", cartId=" + cartId +
                ", count=" + count +
                ", search='" + search + '\'' +
                ", synthesize=" + synthesize +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", quality=" + quality +
                ", bannerIndexId=" + bannerIndexId +
                ", businessId=" + businessId +
                ", jumpType=" + jumpType +
                ", title='" + title + '\'' +
                '}';
    }
}
