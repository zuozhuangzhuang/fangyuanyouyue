package com.fangyuanyouyue.forum.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "论坛相关参数")
public class ForumParam{
	//公用
	@ApiModelProperty(name = "start", value = "起始页", dataType = "Integer",hidden = true)
	private Integer start; // 起始页

	@ApiModelProperty(name = "limit", value = "限制页", dataType = "Integer",hidden = true)
	private Integer limit; // 限制页

	@ApiModelProperty(name = "type", value = "类型", dataType = "Integer",hidden = true)
	private Integer type;//类型

	@ApiModelProperty(name = "forumId", value = "帖子id", dataType = "Integer",hidden = true)
	private Integer forumId;//帖子id

	
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getForumId() {
		return forumId;
	}

	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	
}
