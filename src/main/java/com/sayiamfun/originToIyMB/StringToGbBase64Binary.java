package com.sayiamfun.originToIyMB;

import java.util.Base64;

public class StringToGbBase64Binary {

	public static void main(String[] args) {

		String str = "232302fe4c46563041323447364a333935303930360101431307011212360101030100ed0002e6bc0d912bca01012e239b2400020101015b550b553f8c0d822b84040106660348050006d65a9f02018b7e06012f0da601410d96010d47010245070000000000000000000801010d912bca00680001680d9c0d9b0d990d9b0d990d9a0d9e0d9e0d9b0d9f0da00da00da10d9a0d990d9a0d9a0d9a0d9a0d980d9a0d990d990d980d9c0d990d9c0d9b0d9b0d9b0d9b0d990d9b0d9d0d9d0d9a0d9e0d9c0d9c0da50da30da20da30da30da40da30da60da30da60da60da30da30d970d9b0d970d970d980d9b0d9a0d9b0d9a0d9b0d9a0d9a0d960d9d0d9c0d9c0d9e0d9a0d9a0d9d0d9a0d990d990d9b0d9d0d9a0d990d9a0d990d9b0d9a0d9b0d9d0d9f0d9d0d9e0d9f0d9d0d9b0d990d9c0d990d9a0d990d9b0d980d9a0d9b0d9b0d9a0d990d9d090101001046454645464646454645464647464645f3";
		System.out.println(toBytesBase64(str));
	}

	static String toBytesBase64(String source){
		if (null == source 
				|| "".equals(source.trim()) 
				|| source.length() < 4) {
			
			return null;
		}
		
		source = source.trim();
		if (source.length() % 2 == 0) {
			byte [] bytes = hexStr2Str(source);
			return Base64.getEncoder().encodeToString(bytes);
		}
		return null;
		
	}

	/**
	 * 16进制字符串直接转换成为bytes(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    int len = hexs.length/2;
	    byte[] bytes = new byte[len];
	    int n;
	    for (int i = 0; i < len; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return bytes;
	}
	
}
