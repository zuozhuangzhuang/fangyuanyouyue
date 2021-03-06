package com.fangyuanyouyue.forum.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangyuanyouyue.forum.constants.StatusEnum;
import com.fangyuanyouyue.forum.dao.ForumCommentMapper;
import com.fangyuanyouyue.forum.dto.ForumCommentDto;
import com.fangyuanyouyue.forum.model.ForumComment;
import com.fangyuanyouyue.forum.service.ForumCommentService;
import com.fangyuanyouyue.forum.utils.ServiceException;


@Service(value = "forumCommentService")
public class ForumCommentServiceImp implements ForumCommentService {

    @Autowired
    private ForumCommentMapper forumCommentMapper;
    
	public Integer countComment(Integer forumId) {
		return forumCommentMapper.countById(forumId);
	}

	@Override
	public List<ForumCommentDto> getCommentList(Integer forumId, Integer start, Integer limit) throws ServiceException {
		List<ForumComment> list = forumCommentMapper.selectByForumId(forumId, start, limit);
		return ForumCommentDto.toDtoList(list);
	}

	@Override
	public void saveComment(Integer userId, Integer forumId, String content, Integer commentId) throws ServiceException{
		ForumComment model = new ForumComment();
		model.setUserId(userId);
		model.setForumId(forumId);
		model.setUserId(userId);
		model.setAddTime(new Date());
		model.setContent(content);
		model.setStatus(StatusEnum.STATUS_NORMAL.getValue());
		forumCommentMapper.insert(model);
	}

	@Override
	public void deleteComment(Integer commentId) {
		forumCommentMapper.deleteByPrimaryKey(commentId);
	}

}
