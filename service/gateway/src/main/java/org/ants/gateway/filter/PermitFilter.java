package org.ants.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.Result;
import org.ants.common.utils.EncryptUtil;
import org.ants.gateway.entity.PermitUrlEntity;
import org.ants.gateway.feign.client.RbacClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.util.UrlPathHelper;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @ClassName: PermitFilter
 * @Description: 请求url权限检查
 * @author: Jerry
 * @date: 2018年12月18日 上午10:43:33
 */
public class PermitFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(PermitFilter.class);
	
	private static final Gson GSON = new Gson();
    @Autowired
    private RbacClient rbacClient;
    private PermitUrlEntity permitUrl;
    
    private final RouteLocator routeLocator;
    private final UrlPathHelper urlPathHelper;

    /**
     * 获取请求路径，利用RouteLocator返回路由信息
     * @param request
     * @return
     */
    protected Route route(HttpServletRequest request){
        String requestURI = urlPathHelper.getPathWithinApplication(request);
        return routeLocator.getMatchingRoute(requestURI);
    }
    
    public PermitFilter(RouteLocator routeLocator, PermitUrlEntity permitUrl) {
    	super();
    	this.permitUrl = permitUrl;
    	this.routeLocator = routeLocator;
        this.urlPathHelper = new UrlPathHelper();
    }
	/**
     * 指定该Filter的类型
     * ERROR_TYPE = "error";
     * POST_TYPE = "post";
     * PRE_TYPE = "pre";
     * ROUTE_TYPE = "route";
     */
	@Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 指定该Filter执行的顺序（Filter从小到大执行）
     * DEBUG_FILTER_ORDER = 1;
     * FORM_BODY_WRAPPER_FILTER_ORDER = -1;
     * PRE_DECORATION_FILTER_ORDER = 5;
     * RIBBON_ROUTING_FILTER_ORDER = 10;
     * SEND_ERROR_FILTER_ORDER = 0;
     * SEND_FORWARD_FILTER_ORDER = 500;
     * SEND_RESPONSE_FILTER_ORDER = 1000;
     * SIMPLE_HOST_ROUTING_FILTER_ORDER = 100;
     * SERVLET_30_WRAPPER_FILTER_ORDER = -2;
     * SERVLET_DETECTION_FILTER_ORDER = -3;
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 指定需要执行该Filter的规则
     * 返回true则执行run()
     * 返回false则不执行run()
     */
    @Override
    public boolean shouldFilter() {
    	if (this.permitUrl.isPermitAllUrl(RequestContext.getCurrentContext().getRequest().getRequestURI())) {
    		return false;
    	}
    	
    	return true;
    }

    /**
     * 该Filter具体的执行活动
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Route route = route(request);
        
        StringBuilder resSrc = new StringBuilder();
        resSrc.append(route.getPath().toLowerCase());
        resSrc.append(request.getMethod().toLowerCase());
        resSrc.append(route.getLocation());
        String resId = EncryptUtil.md5(resSrc.toString());
        
        String rlt = null;
        try {
        	rlt = rbacClient.checkPermit(resId);
        } catch (Exception e) {
        	String errorMsg = e.getCause().getMessage();
        	LOGGER.error("checkPermit 错误: {}", errorMsg);
        	ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(200);
            // 为使得中文字符不乱码
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.setResponseBody(GSON.toJson(Result.fail(ErrorConstants.SE_INTERNAL.getCode(), errorMsg)));
        	return null;
        }
        Result permitResult = GSON.fromJson(rlt, Result.class);
        if (!permitResult.isSuccess()) {
        	ctx.setSendZuulResponse(false);
        	
            ctx.setResponseStatusCode(200);
            // 为使得中文字符不乱码
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.setResponseBody(GSON.toJson(Result.fail(ErrorConstants.SE_UNAUTHORIZED)));
        }
   
        return null;
    }
}
