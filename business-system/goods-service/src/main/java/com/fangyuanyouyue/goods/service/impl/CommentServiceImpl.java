package com.fangyuanyouyue.goods.service.impl;

import com.fangyuanyouyue.goods.config.PageHelperConfig;
import com.fangyuanyouyue.goods.dao.GoodsCommentMapper;
import com.fangyuanyouyue.goods.dto.GoodsCommentDto;
import com.fangyuanyouyue.goods.model.GoodsComment;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.service.CommentService;
import com.fangyuanyouyue.goods.utils.DateStampUtils;
import com.fangyuanyouyue.goods.utils.ServiceException;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService{
    @Autowired
    private GoodsCommentMapper goodsCommentMapper;
    @Autowired
    private GoodsCommentMapper goodsCommentMapperl;


    @Override
    public void addComment(GoodsParam param) throws ServiceException {
        GoodsComment goodsComment = new GoodsComment();
        goodsComment.setAddTime(DateStampUtils.getTimesteamp());
        goodsComment.setUpdateTime(DateStampUtils.getTimesteamp());
        if(param.getCommentId() != null){
            goodsComment.setCommentId(param.getCommentId());
        }
        goodsComment.setUserId(param.getUserId());
        if(StringUtils.isNotEmpty(param.getImg1Url())){
            goodsComment.setImg1Url(param.getImg1Url());
        }
        if(StringUtils.isNotEmpty(param.getImg2Url())){
            goodsComment.setImg1Url(param.getImg2Url());
        }
        if(StringUtils.isNotEmpty(param.getImg3Url())){
            goodsComment.setImg1Url(param.getImg3Url());
        }
        goodsComment.setStatus(1);//状态 1正常 2隐藏
        goodsComment.setContent(param.getContent());
        goodsComment.setGoodsId(param.getGoodsId());
        goodsComment.setLikesCount(0);//点赞数初始值为0
        goodsCommentMapper.insert(goodsComment);
    }

    @Override
    public void commentLikes(Integer commentId) throws ServiceException {
        GoodsComment goodsComment = goodsCommentMapper.selectByPrimaryKey(commentId);
        if(goodsComment == null){
            throw new ServiceException("获取评论失败！");
        }else{
            goodsComment.setLikesCount(goodsComment.getLikesCount()+1);
            goodsComment.setUpdateTime(DateStampUtils.getTimesteamp());
            goodsCommentMapper.updateByPrimaryKey(goodsComment);
        }
    }

    @Override
    public List<GoodsCommentDto> getComments(Integer goodsId) throws ServiceException {
        List<Map<String, Object>> maps = goodsCommentMapper.selectByGoodsId( goodsId);
        List<GoodsCommentDto> goodsCommentDtos = GoodsCommentDto.mapToDtoList(maps);
        for(GoodsCommentDto goodsCommentDto:goodsCommentDtos){
            goodsCommentDto.setReplys(selectCommentList(goodsCommentDto.getId(),goodsId));
        }
        return goodsCommentDtos;
    }

    /**
     * 递归获取评论及回复
     * @param commentId
     * @param goodsId
     * @return
     */
    private List<GoodsCommentDto> selectCommentList(Integer commentId,Integer goodsId){
        //根据评论的ID和商品的ID获取回复列表
        List<Map<String, Object>> maps = goodsCommentMapper.selectMapByGoodsIdCommentId(commentId, goodsId,null,null);
        List<GoodsCommentDto> goodsCommentDtos = GoodsCommentDto.mapToDtoList(maps);
        if(goodsCommentDtos != null && goodsCommentDtos.size()>0){
            for(GoodsCommentDto goodsCommentDto:goodsCommentDtos){
                //获取被评论人的信息
                Map<String, Object> map = goodsCommentMapperl.selectByCommentId(goodsCommentDto.getCommentId());
                if(map != null){
                    goodsCommentDto.setToUserId((Integer)map.get("user_id"));
                    goodsCommentDto.setToUserHeadImgUrl((String)map.get("head_img_url"));
                    goodsCommentDto.setToUserName((String)map.get("nick_name"));
                }
                //重复获取此回复的回复列表，直到没有回复了为止
                goodsCommentDto.setReplys(selectCommentList(goodsCommentDto.getId(),goodsId));
            }
        }
        return goodsCommentDtos;
    }
}
