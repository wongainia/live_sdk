package com.lib.showfield.utils;

import android.util.Base64;

/**
 * @description: base64工具类S
 * @autour:
 * @date: 2017/6/21 10:23
 *
*/
public class Base64Util {
	
	/**
	 * encode   转码
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		return strBase64;
	}
	
	/**
	 * decode   解码
	 * @param str
	 * @return
	 */
	public static String decode(String str) {
		String strdata = new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
		return strdata;
	}
	
}
