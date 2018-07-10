package com.fangyuanyouyue.goods.model;

import java.util.Date;

/**
 * 商品鉴定表
 */
public class GoodsAppraisal {
    private Integer id;//唯一自增ID

    private Integer userId;//用户id

    private Integer type;//鉴定类型 1商家鉴定 2买家

    private Date addTime;//添加时间

    private Date updateTime;//更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}