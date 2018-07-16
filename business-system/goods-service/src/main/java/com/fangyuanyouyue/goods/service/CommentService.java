package com.fangyuanyouyue.goods.service;

import com.fangyuanyouyue.goods.dto.GoodsCommentDto;
import com.fangyuanyouyue.goods.param.GoodsParam;
import com.fangyuanyouyue.goods.utils.ServiceException;

import java.util.List;

public interface CommentService {
    /**
     * 增加评论/回复
     * @param param
     * @throws ServiceException
     */
    void addComment(GoodsParam param) throws ServiceException;

    /**
     * 评论点赞
     * @param commentId
     * @throws ServiceException
     */
    void commentLikes(Integer commentId) throws ServiceException;

    /**
     * 查看全部评论
     * @param goodsId
     * @return
     * @throws ServiceException
     */
    List<GoodsCommentDto> getComments(Integer goodsId) throws ServiceException;
}
