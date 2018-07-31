package com.fangyuanyouyue.forum.constants;

public enum StatusEnum {
	
	STATUS_NORMAL(0),STATUS_FROZEN(1);

	StatusEnum(Integer value){
		
	}
	
	private Integer value;
	
	public Integer getValue() {
		return value;
	}
	
}
