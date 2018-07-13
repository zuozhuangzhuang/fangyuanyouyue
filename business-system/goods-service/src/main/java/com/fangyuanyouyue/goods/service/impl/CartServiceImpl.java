package com.fangyuanyouyue.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fangyuanyouyue.goods.dao.*;
import com.fangyuanyouyue.goods.dto.CartDetailDto;
import com.fangyuanyouyue.goods.dto.CartShopDto;
import com.fangyuanyouyue.goods.model.CartDetail;
import com.fangyuanyouyue.goods.model.CartInfo;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.service.CartService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
import com.fangyuanyouyue.goods.utils.DateStampUtils;
import com.fangyuanyouyue.goods.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cartService")
public class CartServiceImpl implements CartService{
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private CartDetailMapper cartDetailMapper;
    @Autowired
    private CartInfoMapper cartInfoMapper;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用

    @Value("${pic_server:errorPicServer}")
    private String PIC_SERVER;// 图片服务器

    @Value("${pic_path:errorPicPath}")
    private String PIC_PATH;// 图片存放路径


    @Override
    public void addGoodsToCart(Integer userId, Integer goodsId) throws ServiceException {
        //获取购物车信息
        CartInfo cartInfo = cartInfoMapper.selectByUserId(userId);
        if(cartInfo == null){
            //购物车不存在就新增
            cartInfo = new CartInfo();
            cartInfo.setUserId(userId);
            cartInfo.setAddTime(DateStampUtils.getTimesteamp());
            cartInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            cartInfoMapper.insert(cartInfo);
        }else{
            //购物车存在就修改update_time
            cartInfo.setUpdateTime(DateStampUtils.getTimesteamp());
            cartInfoMapper.updateByPrimaryKey(cartInfo);

            GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
            //判断商品还在不
            if(goodsInfo == null){
                throw new ServiceException("商品不存在或已下架！");
            }else{
                CartDetail cartDetail = cartDetailMapper.selectByCartIdGoodsId(cartInfo.getId(),goodsId);
                //判断购物车是否已经有这个商品了
                if(cartDetail == null){
                    cartDetail = new CartDetail();
                    JSONObject user = JSONObject.parseObject(JSONObject.parseObject(schedualUserService.verifyUserById(goodsInfo.getUserId())).getString("data"));
                    cartDetail.setAddTime(DateStampUtils.getTimesteamp());
                    cartDetail.setUpdateTime(DateStampUtils.getTimesteamp());
                    cartDetail.setCartId(cartInfo.getId());
                    cartDetail.setGoodsId(goodsId);
                    cartDetail.setPrice(goodsInfo.getPrice());
                    cartDetail.setUserId(goodsInfo.getUserId());
                    cartDetail.setNickName(user.getString("nickName"));
                    cartDetail.setHeadImgUrl(user.getString("headImgUrl"));
                    cartDetail.setStatus(1);//商品是否显示 1显示2不显示
                    cartDetailMapper.insert(cartDetail);
                }else{
                    throw new ServiceException("商品已存在购物车中，请勿重复添加！");
                }
            }
        }
    }

    @Override
    public List<CartShopDto> getCart(Integer userId) throws ServiceException {
        CartInfo cart = cartInfoMapper.selectByUserId(userId);
        //购物车是否存在
        if(cart == null){
            throw new ServiceException("购物车空空如也！");
        }else{
            List<CartShopDto> cartShopDtos = new ArrayList<>();
            List<CartDetail> cartDetails = cartDetailMapper.selectByCartId(cart.getId());
            if(cartDetails != null){
                for(CartDetail cartDetail:cartDetails){
                    CartShopDto cartShopDto=new CartShopDto();
                    cartShopDto.setUserId(cartDetail.getUserId());
                    cartShopDto.setHeadImgUrl(cartDetail.getHeadImgUrl());
                    cartShopDto.setNickName(cartDetail.getNickName());
                    List<CartDetailDto> cartDetailDtos = CartDetailDto.toDtoList(cartDetailMapper.selectByCartIdUserId(cart.getId(), cartDetail.getUserId()));
                    cartShopDto.setCartDetail(cartDetailDtos);
                    cartShopDtos.add(cartShopDto);
                }
                return cartShopDtos;
            }else{
                throw new ServiceException("获取购物车详情失败！");
            }
        }
    }

    @Override
    public void cartRemove(Integer[] cartDetailIds) throws ServiceException {
        for(Integer cartDetailId:cartDetailIds){
            CartDetail cartDetail = cartDetailMapper.selectByPrimaryKey(cartDetailId);
            if(cartDetail == null){
                throw new ServiceException("购物车详情数据异常！");
            }else{
                cartDetail.setStatus(2);//商品是否显示 1显示2不显示
                cartDetail.setUpdateTime(DateStampUtils.getTimesteamp());
                cartDetailMapper.updateByPrimaryKey(cartDetail);
            }
        }
    }
}
