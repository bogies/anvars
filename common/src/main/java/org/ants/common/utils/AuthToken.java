package org.ants.common.utils;

import org.ants.common.entity.CustomRequestEntity;

import com.google.gson.Gson;

public class AuthToken {
	private static final Gson GSON = new Gson();
	
	public static String makeToken(CustomRequestEntity reqEntity, String key) {
		StringBuilder token = new StringBuilder();
		try {
			String src = SafeUrlBase64.encode(GSON.toJson(reqEntity).getBytes());
			token.append(src);
			String sign = makeSign(src, key);
			token.append(".");
			token.append(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token.toString();
	}
	public static String makeSign(String src, String key) {
		return EncryptUtil.md5(src+key);
	}
}
