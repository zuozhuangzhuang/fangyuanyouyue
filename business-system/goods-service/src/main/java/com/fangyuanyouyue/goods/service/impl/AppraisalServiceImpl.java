package com.fangyuanyouyue.goods.service.impl;

import com.fangyuanyouyue.goods.dao.*;
import com.fangyuanyouyue.goods.model.GoodsAppraisal;
import com.fangyuanyouyue.goods.model.GoodsAppraisalDetail;
import com.fangyuanyouyue.goods.model.GoodsInfo;
import com.fangyuanyouyue.goods.service.AppraisalService;
import com.fangyuanyouyue.goods.service.SchedualUserService;
import com.fangyuanyouyue.goods.utils.DateStampUtils;
import com.fangyuanyouyue.goods.utils.ReCode;
import com.fangyuanyouyue.goods.utils.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(value = "appraisalService")
public class AppraisalServiceImpl implements AppraisalService{
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private GoodsImgMapper goodsImgMapper;
    @Autowired
    private GoodsCorrelationMapper goodsCorrelationMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsCommentMapper goodsCommentMapperl;
    @Autowired
    private HotSearchMapper hotSearchMapper;
    @Autowired
    private BannerIndexMapper bannerIndexMapper;
    @Autowired
    private GoodsQuickSearchMapper goodsQuickSearchMapper;
    @Autowired
    private SchedualUserService schedualUserService;//调用其他service时用
    @Autowired
    private GoodsAppraisalMapper goodsAppraisalMapper;
    @Autowired
    private GoodsAppraisalDetailMapper goodsAppraisalDetailMapper;

    @Override
    public void addAppraisal(Integer userId, Integer[] goodsIds, String title, String description, BigDecimal price,String imgUrl) throws ServiceException {
        //可能提交多个商品鉴定
        GoodsAppraisal goodsAppraisal = new GoodsAppraisal();
        goodsAppraisal.setAddTime(DateStampUtils.getTimesteamp());
        goodsAppraisal.setUserId(userId);
        goodsAppraisalMapper.insert(goodsAppraisal);
        if(goodsIds != null || goodsIds.length != 0){
            for(Integer goodsId:goodsIds){
                GoodsAppraisalDetail goodsAppraisalDetail = goodsAppraisalDetailMapper.selectByUserIdGoodsId(userId, goodsId);
                if(goodsAppraisalDetail != null){
                    throw new ServiceException("您已申请过鉴定！");
                }else{
                    GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(goodsId);
                    if(goodsInfo == null){
                        throw new ServiceException("鉴定列表中包含不存在或已下架商品！");
                    }else{
                        goodsAppraisalDetail = new GoodsAppraisalDetail();
                        goodsAppraisalDetail.setAddTime(DateStampUtils.getTimesteamp());
                        goodsAppraisalDetail.setAppraisalId(goodsAppraisal.getId());
                        goodsAppraisalDetail.setGoodsId(goodsId);
                        goodsAppraisalDetail.setStatus(0);//状态 0申请 1真 2假 3存疑
                        goodsAppraisalDetail.setTitle(title);
                        goodsAppraisalDetail.setDescription(description);
                        goodsAppraisalDetail.setPrice(price);
                        if(goodsInfo.getUserId() == userId){
                            goodsAppraisalDetail.setType(1);//鉴定类型 1商家鉴定 2买家 3普通用户
                            goodsInfo.setIsAppraisal(2);//是否鉴定 1未鉴定 2已鉴定
                        }else{
                            goodsAppraisalDetail.setType(2);
                        }
                        goodsAppraisalDetailMapper.insert(goodsAppraisalDetail);
                    }
                }
            }
        }else{
            GoodsAppraisalDetail goodsAppraisalDetail = new GoodsAppraisalDetail();
            goodsAppraisalDetail.setAddTime(DateStampUtils.getTimesteamp());
            goodsAppraisalDetail.setAppraisalId(goodsAppraisal.getId());
            goodsAppraisalDetail.setStatus(0);//状态 0申请 1真 2假 3存疑
            goodsAppraisalDetail.setTitle(title);
            goodsAppraisalDetail.setType(3);//鉴定类型 1商家鉴定 2买家 3普通用户
            goodsAppraisalDetail.setDescription(description);
            goodsAppraisalDetail.setPrice(price);
            goodsAppraisalDetailMapper.insert(goodsAppraisalDetail);
        }
    }
}
