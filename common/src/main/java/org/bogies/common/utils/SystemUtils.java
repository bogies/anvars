package org.bogies.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取系统的信息
 * 
 * @author liuyazhuang
 * 
 */
public final class SystemUtils {
	/**
	 * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(String ip, String xForwardedFor, String remoteAddr) {
		if (null != ip && "".equals("") && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = xForwardedFor;
		if (null != ip && "".equals("") && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return remoteAddr;
		}
	}

	/**
	 * 获取来访者的浏览器版本
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestBrowserInfo(String header) {
		String browserVersion = null;
		if (header == null || header.equals("")) {
			return "";
		}
		if (header.indexOf("MSIE") > 0) {
			browserVersion = "IE";
		} else if (header.indexOf("Firefox") > 0) {
			browserVersion = "Firefox";
		} else if (header.indexOf("Chrome") > 0) {
			browserVersion = "Chrome";
		} else if (header.indexOf("Safari") > 0) {
			browserVersion = "Safari";
		} else if (header.indexOf("Camino") > 0) {
			browserVersion = "Camino";
		} else if (header.indexOf("Konqueror") > 0) {
			browserVersion = "Konqueror";
		}
		return browserVersion;
	}

	/**
	 * 获取系统版本信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestSystemInfo(String header) {
		String systenInfo = null;
		if (header == null || header.equals("")) {
			return "";
		}
		// 得到用户的操作系统
		if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista/Server 2008";
		} else if (header.indexOf("NT 5.2") > 0) {
			systenInfo = "Windows Server 2003";
		} else if (header.indexOf("NT 5.1") > 0) {
			systenInfo = "Windows XP";
		} else if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista";
		} else if (header.indexOf("NT 6.1") > 0) {
			systenInfo = "Windows 7";
		} else if (header.indexOf("NT 6.2") > 0) {
			systenInfo = "Windows Slate";
		} else if (header.indexOf("NT 6.3") > 0) {
			systenInfo = "Windows 9";
		} else if (header.indexOf("NT 5") > 0) {
			systenInfo = "Windows 2000";
		} else if (header.indexOf("NT 4") > 0) {
			systenInfo = "Windows NT4";
		} else if (header.indexOf("Me") > 0) {
			systenInfo = "Windows Me";
		} else if (header.indexOf("98") > 0) {
			systenInfo = "Windows 98";
		} else if (header.indexOf("95") > 0) {
			systenInfo = "Windows 95";
		} else if (header.indexOf("Mac") > 0) {
			systenInfo = "Mac";
		} else if (header.indexOf("Unix") > 0) {
			systenInfo = "UNIX";
		} else if (header.indexOf("Linux") > 0) {
			systenInfo = "Linux";
		} else if (header.indexOf("SunOS") > 0) {
			systenInfo = "SunOS";
		}
		return systenInfo;
	}

	/**
	 * 获取来访者的主机名称
	 * 
	 * @param ip
	 * @return
	 */
	public static String getHostName(String ip) {
		InetAddress inet;
		try {
			inet = InetAddress.getByName(ip);
			return inet.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 执行系统命令
	 * 
	 * @param cmd
	 * @return
	 */
	public static String callCmd(String[] cmd) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}