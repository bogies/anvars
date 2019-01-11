package org.ants.common.constants;
/**
 * @ClassName: SecurityConstants
 * @Description: TODO
 * @author: jerry
 * @date: 2018年12月12日 下午2:21:13
 */
public class RequestHeaderConstants {
	/**
	 * Gateway服务生成的TOKEN
	 */
    public static final String AUTH_TOKEN = "X-Auth-Token";
    /**
     * 用户id
     */
    public static final String USER_ID = "X-User-ID";
    /**
     * view 请求服务的地址
     */
    public static final String REQUEST_URI = "V-Req-Uri";
    /**
     * view 请求服务的方法
     */
    public static final String REQUEST_METHOD = "V-Req-Method";
}
