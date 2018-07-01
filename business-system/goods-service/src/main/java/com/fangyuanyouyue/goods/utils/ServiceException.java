package com.fangyuanyouyue.goods.utils;

/**
 * 业务处理异常
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
		super(msg);
	}
}
