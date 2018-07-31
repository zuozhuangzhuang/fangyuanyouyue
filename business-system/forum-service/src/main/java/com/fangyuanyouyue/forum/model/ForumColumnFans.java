package com.fangyuanyouyue.forum.model;

import java.util.Date;

/**
 *专栏粉丝表
 */
public class ForumColumnFans {
    private Integer id;//唯一自增ID

    private Integer userId;//用户id

    private Integer columnId;//专栏id

    private Date addTime;//添加时间

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

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}