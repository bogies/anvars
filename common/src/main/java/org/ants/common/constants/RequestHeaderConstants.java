package org.ants.common.constants;
/**
 * @ClassName: SecurityConstants
 * @Description: RESTful 请求header字段定义
 * @author: jerry
 * @date: 2018年12月12日 下午2:21:13
 */
public class RequestHeaderConstants {
	public static final String REQUEST_URI = "XV-URI";
	public static final String REQUEST_METHOD = "XV-METHOD";
	/**
	 * 认证token, jwt
	 */
    public static final String AUTH = "X-AUTH";
    /**
     * 用户ID
     */
    public static final String USER_ID = "X-UID";
    /**
     * 用户名
     */
    public static final String USERNAME = "X-UNAME";
    /**
     * 用户IP
     */
    public static final String USER_IP = "X-UIP";
    /**
     * 服务请求id
     */
    public static final String REQUEST_ID = "X-REQID";
    /**
     * 服务调用者
     */
    public static final String CALLER = "X-CALLER";
}
