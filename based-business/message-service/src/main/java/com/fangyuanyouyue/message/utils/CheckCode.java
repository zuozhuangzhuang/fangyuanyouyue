package com.fangyuanyouyue.message.utils;

import java.util.Random;


/**
 * 获取验证码
 * @author Administrator
 *
 * @date 2014-11-12 下午4:37:47
 */
public class CheckCode {
	
	static char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 

	
	/**
	 * 随机获取四位验证码
	 * @return
	 */
	public static String getCheckCode(){
	
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		
		for (int i = 0; i <  4; i++) {
			//得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
			sb.append(strRand);
		}
		
		return sb.toString();
	}
	
	public static void main(String... args){
		System.out.println(getCheckCode());
	}

}
