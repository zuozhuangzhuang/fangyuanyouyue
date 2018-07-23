package com.fangyuanyouyue.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.order.dao.OrderDetailMapper;
import com.fangyuanyouyue.order.dao.OrderInfoMapper;
import com.fangyuanyouyue.order.dao.OrderPayMapper;
import com.fangyuanyouyue.order.dto.OrderDetailDto;
import com.fangyuanyouyue.order.dto.OrderDto;
import com.fangyuanyouyue.order.dto.OrderPayDto;
import com.fangyuanyouyue.order.model.OrderDetail;
import com.fangyuanyouyue.order.model.OrderInfo;
import com.fangyuanyouyue.order.model.OrderPay;
import com.fangyuanyouyue.order.service.OrderService;
import com.fangyuanyouyue.order.service.SchedualGoodsService;
import com.fangyuanyouyue.order.service.SchedualUserService;
import com.fangyuanyouyue.order.utils.DateStampUtils;
import com.fangyuanyouyue.order.utils.IdGenerator;
import com.fangyuanyouyue.order.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private SchedualGoodsService schedualGoodsService;
    @Autowired
    private SchedualUserService schedualUserService;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderPayMapper orderPayMapper;


    @Override
    public OrderDto saveOrder(String token,Integer[] goodsIds, Integer userId, Integer addressId,Integer type) throws ServiceException {
        //获取收货地址
        String result = schedualUserService.getAddressList(token,addressId);
        JSONArray addressArray = JSONArray.parseArray(JSONObject.parseObject(result).getString("data"));
            JSONObject address = null;
        if(addressArray != null && addressArray.size()>0){
            address = JSONObject.parseObject(addressArray.get(0).toString());
        }
        if(address == null || StringUtils.isEmpty(address.getString("address")) || StringUtils.isEmpty(address.getString("province"))
                || StringUtils.isEmpty(address.getString("city")) || StringUtils.isEmpty(address.getString("area"))
                || StringUtils.isEmpty(address.getString("area")) || StringUtils.isEmpty(address.getString("area"))){
            throw new  ServiceException("收货地址异常，请先更新地址");
        }
        //生成总订单，包含多条订单详情
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        //订单号
        final IdGenerator idg = IdGenerator.INSTANCE;
        String id = idg.nextId();
        orderInfo.setOrderNo("1"+id);

        BigDecimal amount = new BigDecimal(0);//总金额，初始为0
        orderInfo.setAmount(amount);
        orderInfo.setCount(goodsIds.length);
        orderInfo.setStatus(1);//状态 1待支付 2待发货 3待收货 4已完成 5已取消 7已申请退货
        orderInfo.setAddTime(DateStampUtils.getTimesteamp());
        orderInfoMapper.insert(orderInfo);
        //生成订单支付表
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderId(orderInfo.getId());
        orderPay.setReceiverName(address.getString("receiverName"));
        orderPay.setReceiverPhone(address.getString("receiverPhone"));
        orderPay.setAddress(address.getString("address"));
        orderPay.setPostCode(address.getString("postCode"));
        orderPay.setOrderNo(orderInfo.getOrderNo());
        orderPay.setAmount(amount);
        orderPay.setPayAmount(amount);
        orderPay.setFreight(amount);//运费金额，初始化为0
        orderPay.setCount(goodsIds.length);
//        orderPay.setPayType();
//        orderPay.setPayNo();
//        orderPay.setPayTime();
//        orderPay.setSendType();
//        orderPay.setLogisticStatus();
//        orderPay.setLogisticCode();
//        orderPay.setLogisticCompany();
//        orderPay.setRemarks();
        orderPay.setStatus(1);//状态 1待支付 2待发货 3待收货 4已完成 5已取消 7已申请退货
        orderPay.setAddTime(DateStampUtils.getTimesteamp());
        orderPayMapper.insert(orderPay);

        List<OrderDetail> orderDetails = new ArrayList<>();
        if(type == 1){//状态 1普通商品
            for(Integer goodsId:goodsIds){
                JSONObject goods = JSONObject.parseObject(JSONObject.parseObject(schedualGoodsService.goodsInfo(goodsId)).getString("data"));
                if(goods == null){
    //                throw new ServiceException("商品异常！");
                    continue;
                }else{
                    //计算总订单总金额
                    //每个商品生成一个订单详情表
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setUserId(userId);
                    orderDetail.setOrderId(orderInfo.getId());
                    orderDetail.setGoodsId(goodsId);
                    orderDetail.setGoodsName(goods.getString("name"));
                    orderDetail.setAddTime(DateStampUtils.getTimesteamp());
                    //修改商品的状态为已售出
                    schedualGoodsService.updateGoodsStatus(goodsId,2);//状态  1出售中 2已售出 5删除

                    orderDetailMapper.insert(orderDetail);
                    orderDetails.add(orderDetail);
                    amount = amount.add(goods.getBigDecimal("price"));
                }
            }
        }else if(type == 2){// 2抢购商品
            JSONObject goods = JSONObject.parseObject(JSONObject.parseObject(schedualGoodsService.goodsInfo(goodsIds[0])).getString("data"));
            if(goods == null){
                throw new ServiceException("抢购异常！");
            }else {
                //计算总订单总金额
                //每个商品生成一个订单详情表
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setUserId(userId);
                orderDetail.setOrderId(orderInfo.getId());
                orderDetail.setGoodsId(goodsIds[0]);
                orderDetail.setGoodsName(goods.getString("name"));
                orderDetail.setAddTime(DateStampUtils.getTimesteamp());
                orderDetailMapper.insert(orderDetail);
                orderDetails.add(orderDetail);
                amount = amount.add(goods.getBigDecimal("price"));
                //修改商品的状态为已售出
                schedualGoodsService.updateGoodsStatus(goodsIds[0],2);//状态  1出售中 2已售出 5删除
            }
        }else{
            throw new ServiceException("商品状态错误！");
        }
        orderInfo.setAmount(amount);
        orderInfoMapper.updateByPrimaryKey(orderInfo);
        orderPay.setAmount(amount);
        //计算优惠券后价格
        orderPay.setPayAmount(amount);
        orderPayMapper.updateByPrimaryKey(orderPay);

        OrderPayDto orderPayDto = new OrderPayDto(orderPay);
        OrderDto orderDto = new OrderDto(orderInfo);
        orderDto.setOrderPayDto(orderPayDto);
        ArrayList<OrderDetailDto> orderDetailDtos = OrderDetailDto.toDtoList(orderDetails);
        orderDto.setOrderDetailDtos(orderDetailDtos);

        return orderDto;

    }

    @Override
    public void cancelOrder(Integer userId, Integer orderId) throws ServiceException {
        //根据订单ID获取订单信息
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        OrderPay orderPay = orderPayMapper.selectByOrderId(orderId);
        if(orderInfo != null && orderPay != null){
            //更改订单状态
            orderInfo.setStatus(5);//状态 1待支付 2待发货 3待收货 4已完成 5已取消  7已申请退货
            orderPay.setStatus(5);
            orderInfoMapper.updateByPrimaryKey(orderInfo);
            orderPayMapper.updateByPrimaryKey(orderPay);
            //获取此订单内所有商品，更改商品状态为出售中
            List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
            for(OrderDetail orderDetail:orderDetails){
                schedualGoodsService.updateGoodsStatus(orderDetail.getGoodsId(),1);//状态 1出售中 2已售出 5删除
            }
        }else{
            throw new ServiceException("订单异常！");
        }
    }

    @Override
    public OrderDto orderDetail(Integer userId, Integer orderId) throws ServiceException {
        //根据订单ID获取订单信息
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        OrderPay orderPay = orderPayMapper.selectByOrderId(orderId);
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(orderInfo != null && orderPay != null){
            //更改订单状态
            orderInfo.setStatus(5);//状态 1待支付 2待发货 3待收货 4已完成 5已取消  7已申请退货
            orderPay.setStatus(5);
            orderInfoMapper.updateByPrimaryKey(orderInfo);
            orderPayMapper.updateByPrimaryKey(orderPay);
            //获取此订单内所有商品，更改商品状态为出售中
            orderDetails = orderDetailMapper.selectByOrderId(orderId);
            for(OrderDetail orderDetail:orderDetails){
                schedualGoodsService.updateGoodsStatus(orderDetail.getGoodsId(),1);//状态 1出售中 2已售出 5删除
            }
        }else{
            throw new ServiceException("订单异常！");
        }
        OrderPayDto orderPayDto = new OrderPayDto(orderPay);
        OrderDto orderDto = new OrderDto(orderInfo);
        orderDto.setOrderPayDto(orderPayDto);
        ArrayList<OrderDetailDto> orderDetailDtos = OrderDetailDto.toDtoList(orderDetails);
        orderDto.setOrderDetailDtos(orderDetailDtos);
        return orderDto;
    }
}
