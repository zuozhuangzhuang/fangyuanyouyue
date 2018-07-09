package com.fangyuanyouyue.order.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 后台请求基础参数
 */
@ApiModel(value = "后台请求基础参数")
public class BaseParam {

	@ApiModelProperty(name = "start", value = "起始页", dataType = "Integer",hidden = true)
	private Integer start; // 起始页
	@ApiModelProperty(name = "limit", value = "限制页", dataType = "Integer",hidden = true)
	private Integer limit; // 限制页
	@ApiModelProperty(name = "name", value = "搜素字段", dataType = "Integer",hidden = true)
	private String name; // 搜素字段
	@ApiModelProperty(name = "type", value = "类型", dataType = "Integer",hidden = true)
	private Integer type;//类型 goodsInfo：1普通商品 2秒杀商品 goodsCategory：1主图 2次图 goodsCategory：1普通 2热门

	public BaseParam() {

	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
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
