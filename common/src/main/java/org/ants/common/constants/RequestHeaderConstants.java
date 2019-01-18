package org.ants.common.constants;
/**
 * @ClassName: SecurityConstants
 * @Description: TODO
 * @author: jerry
 * @date: 2018年12月12日 下午2:21:13
 */
public class RequestHeaderConstants {
	/**
	 * 服务生成的签名
	 */
    public static final String AUTH_TOKEN = "X-Auth-Token";
    /**
     * 请求者的IP
     */
    public static final String CLIENT_IP = "X-Client-IP";
    /**
     * 请求序列号
     */
    public static final String REQUEST_ID = "X-Req-ID";
    /**
     * view 请求服务的地址
     */
    public static final String REQUEST_URI = "V-Req-Uri";
    /**
     * view 请求服务的方法
     */
    public static final String REQUEST_METHOD = "V-Req-Method";
}
