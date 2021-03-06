package com.fangyuanyouyue.forum.service;

import java.util.List;

import com.fangyuanyouyue.forum.dto.ForumColumnDto;
import com.fangyuanyouyue.forum.utils.ServiceException;

/**
 * 专栏接口
 * @author wuzhimin
 *
 */
public interface ForumColumnService {

	/**
	 * 获取专栏列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
    List<ForumColumnDto> getColumnList(Integer start,Integer limit) throws ServiceException;
 

}
