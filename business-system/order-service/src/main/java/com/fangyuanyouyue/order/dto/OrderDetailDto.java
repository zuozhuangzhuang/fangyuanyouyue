package com.fangyuanyouyue.order.dto;

import com.fangyuanyouyue.order.model.OrderDetail;
import com.fangyuanyouyue.order.model.OrderPay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情表DTO
 */
public class OrderDetailDto {
    private Integer userId;//用户id

    private Integer orderId;//订单id

    private Integer goodsId;//商品id

    private String goodsName;//商品标题

    private BigDecimal price;//商品价格

    private BigDecimal orgPrice;//商品原价

    private BigDecimal freight;//运送费

//    private BigDecimal discount;//活动优惠

    private String iconImg;//商品主图

    private Integer status;//状态 1待支付 2待发货 3待收货 4已完成 5已取消  7已申请退货

    private String allowReturn;//是否可以退货  0可退货  1不可退货

    public OrderDetailDto() {
    }

    public OrderDetailDto(OrderDetail orderDetail) {
        this.userId = orderDetail.getUserId();
        this.orderId = orderDetail.getOrderId();
        this.goodsId = orderDetail.getGoodsId();
        this.goodsName = orderDetail.getGoodsName();
//        this.price = orderDetail
//        this.orgPrice = orderDetail
        this.freight = freight;
        this.iconImg = iconImg;
        this.status = status;
        this.allowReturn = allowReturn;
    }
    public static ArrayList<OrderDetailDto> toDtoList(List<OrderDetail> list) {
        if (list == null)
            return null;
        ArrayList<OrderDetailDto> dtolist = new ArrayList<>();
        for (OrderDetail model : list) {
            OrderDetailDto dto = new OrderDetailDto(model);
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(BigDecimal orgPrice) {
        this.orgPrice = orgPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAllowReturn() {
        return allowReturn;
    }

    public void setAllowReturn(String allowReturn) {
        this.allowReturn = allowReturn;
    }
}
