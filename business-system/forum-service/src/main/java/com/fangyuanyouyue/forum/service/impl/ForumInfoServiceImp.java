package com.fangyuanyouyue.forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangyuanyouyue.forum.dao.ForumCommentMapper;
import com.fangyuanyouyue.forum.dao.ForumInfoMapper;
import com.fangyuanyouyue.forum.dto.ForumInfoDto;
import com.fangyuanyouyue.forum.model.ForumInfo;
import com.fangyuanyouyue.forum.service.ForumInfoService;
import com.fangyuanyouyue.forum.utils.ServiceException;


@Service(value = "forumInfoService")
public class ForumInfoServiceImp implements ForumInfoService {


    @Autowired
    private ForumInfoMapper forumInfoMapper;
    @Autowired
    private ForumCommentMapper forumCommentMapper;
    
	@Override
	public ForumInfoDto getForumInfoById(Integer id) {
		
		ForumInfo forumInfo = forumInfoMapper.selectByPrimaryKey(id);
		
		if(forumInfo!=null) {
			ForumInfoDto dto = new ForumInfoDto(forumInfo);
			//计算点赞数
			
			//计算评论数
			long commentCount = forumCommentMapper.countAll();
			dto.setCommentCount(commentCount);
			
			return dto;
		}
		
		return null;
	}

	@Override
	public List<ForumInfoDto> forumList(String nickName, Integer type, Integer start, Integer limit)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
