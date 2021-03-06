package com.fangyuanyouyue.forum.service;

import java.util.List;

import com.fangyuanyouyue.forum.dto.BannerIndexDto;
import com.fangyuanyouyue.forum.utils.ServiceException;

/**
 * banner接口
 * @author wuzhimin
 *
 */
public interface BannerIndexService {

	/**
	 * 获取banner列表
	 * @param forumId
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
    List<BannerIndexDto> getBannerIndex() throws ServiceException;
 

}
