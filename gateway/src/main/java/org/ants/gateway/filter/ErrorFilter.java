package org.ants.gateway.filter;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * @ClassName: ErrorFilter
 * @Description: 错误捕获
 * @author: Jerry
 * @date: 2019年01月08日
 */
public class ErrorFilter extends ZuulFilter {
	private static final Gson GSON = new Gson();
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorFilter.class);

	/**
     * 指定该Filter的类型
     * ERROR_TYPE = "error";
     * POST_TYPE = "post";
     * PRE_TYPE = "pre";
     * ROUTE_TYPE = "route";
     */
	@Override
    public String filterType() {
        return ERROR_TYPE;
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
        return SEND_ERROR_FILTER_ORDER;
    }

    /**
     * 指定需要执行该Filter的规则
     * 返回true则执行run()
     * 返回false则不执行run()
     */
    @Override
    public boolean shouldFilter() {
    	return true;
    }
    /**
     * 该Filter具体的执行活动
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        String errorMsg = throwable.getCause().getMessage();
        LOGGER.error("错误: {}", errorMsg);
        
        Result rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), errorMsg);
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        // 为使得中文字符不乱码
        ctx.getResponse().setCharacterEncoding("UTF-8");
        ctx.setResponseBody(GSON.toJson(rlt));
   
        return null;
    }
}
