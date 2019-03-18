package org.bogies.common.utils;

import java.util.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月17日上午8:40:36
 */
public class SafeUrlBase64 {
	public static String encode(byte[] srcData){
		String safeBase64Str = "";
		if (null == srcData) {
			return safeBase64Str;
		}
		if (srcData.length == 0) {
			return safeBase64Str;
		}
		byte byPayload[] = Base64.getEncoder().encode(srcData);
		try {
			String encodeBase64 = new String(byPayload, "utf-8");
			safeBase64Str = encodeBase64.replace('+', '-');
			safeBase64Str = safeBase64Str.replace('/', '_');
			safeBase64Str = safeBase64Str.replaceAll("=", "");
		} catch (Exception e) {}
		
		return safeBase64Str;
	}
	
	public static String decode(final String srcData){
		String data = "";
		if (StringUtils.isBlank(srcData)) {
			return data;
		}
		String base64Str = srcData.replace('-', '+');
		base64Str = base64Str.replace('_', '/');
		int mod4 = base64Str.length()%4;
		if(mod4 != 0){
			base64Str += "====".substring(mod4);
		}
		
		byte byData[] = Base64.getDecoder().decode(base64Str);
		try {
			data = new String(byData, "utf-8");
		}catch (Exception e) {}
		
		return data;
	}
}
