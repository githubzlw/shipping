package com.cynergy.listener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5摘要算法的工具类
 * 
 * @author 
 * @since
 */
public class Md5Util {

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String encoder(String source)
	{
		if(source == null || source.equals(""))
		{
			return null;
		}
		return encoder(source.getBytes());
	}
	
	public static String encoder(byte [] source)
	{
		if(source == null || source.length == 0)
		{
			return null;
		}
		byte[] md5Bytes = encoderForBytes(source);
		if (md5Bytes != null) {
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
				{
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString().toUpperCase();
		}
		return null;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	private static byte[] encoderForBytes(String source) {
		return encoderForBytes(source.getBytes());
	}
	
	private static byte[] encoderForBytes(byte[] source)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(source);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
}

