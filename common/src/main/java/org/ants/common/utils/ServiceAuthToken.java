package org.ants.common.utils;

public class ServiceAuthToken {
	public static String makeToken(String serviceName, long timestamp, String secret) {
		String sign = EncryptUtil.md5(serviceName+String.valueOf(timestamp)+secret);
		return sign;
	}
}
