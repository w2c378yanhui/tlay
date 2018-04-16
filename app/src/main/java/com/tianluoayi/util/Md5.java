package com.tianluoayi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @see {@linkplain https://blog.csdn.net/randyjiawenjie/article/details/6589489}
 * 
 * @create 2018年4月13日 下午2:08:49
 */
public class Md5 {
	/**
	 * 将字符串转成MD5值
	 * 
	 * @param string
	 * @return
	 */
	public static String md5(String val) {
		if (val != null) {
			try {
				byte[] hash = MessageDigest.getInstance("MD5").digest(val.getBytes("UTF-8"));
				StringBuilder hex = new StringBuilder(hash.length * 2);
				for (byte b : hash) {
					if ((b & 0xFF) < 0x10)
						hex.append("0");
					hex.append(Integer.toHexString(b & 0xFF));
				}
				return hex.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
