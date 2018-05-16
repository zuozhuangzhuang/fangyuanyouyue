package com.fangyuanyouyue.auth.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 后台请求基础参数
 */
@ApiModel(value = "后台请求基础参数")
public class BaseParam {

	@ApiModelProperty(name = "start", value = "起始页", dataType = "Integer")
	private Integer start; // 起始页
	@ApiModelProperty(name = "limit", value = "限制页", dataType = "Integer")
	private Integer limit; // 限制页
	@ApiModelProperty(name = "name", value = "搜素字段", dataType = "Integer")
	private String name; // 搜素字段
	@ApiModelProperty(name = "type", value = "类型", dataType = "String")
	private String type;//类型

	public BaseParam() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
