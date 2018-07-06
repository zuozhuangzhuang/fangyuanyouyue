package com.fangyuanyouyue.goods.dto;

import com.fangyuanyouyue.goods.model.GoodsComment;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评论Dto
 */
public class GoodsCommentDto {
    //GoodsComment
    private Integer userId;//用户id

    private Integer commentId;//回复评论id

    private String content;//评论内容

    private Integer likesCount;//点赞次数

    private String img1Url;//图片地址1

    private String img2Url;//图片地址2

    private String img3Url;//图片地址3

    private Integer status;//状态 1正常 2隐藏

    public GoodsCommentDto() {
    }

    public GoodsCommentDto(GoodsComment goodsComment) {
        this.userId = goodsComment.getUserId();
        this.commentId = goodsComment.getCommentId();
        this.content = goodsComment.getContent();
        this.likesCount = goodsComment.getLikesCount();
        this.img1Url = goodsComment.getImg1Url();
        this.img2Url = goodsComment.getImg2Url();
        this.img3Url = goodsComment.getImg3Url();
        this.status = goodsComment.getStatus();
    }
    public static List<GoodsCommentDto> toDtoList(List<GoodsComment> list) {
        if (list == null)
            return null;
        List<GoodsCommentDto> dtolist = new ArrayList<>();
        for (GoodsComment model : list) {
            GoodsCommentDto dto = new GoodsCommentDto(model);
            dtolist.add(dto);
        }
        return dtolist;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
