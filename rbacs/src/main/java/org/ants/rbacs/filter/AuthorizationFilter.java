package org.ants.rbacs.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.ants.rbacs.entity.PermitUrlEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import java.io.IOException;

/**
 * @ClassName: AuthorizationFilter
 * @Description: 请求合法性验证
 * @author: Jerry
 * @date: 2018年12月12日 下午5:16:25
 */
@Order(0)
@WebFilter(filterName="authorizationFilter")
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	@Value("${spring.profiles.active}")
	private String profilesActive;
	@Autowired
	private PermitUrlEntity permitUrl;
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	permitUrl.init();
    	LOGGER.debug("加载权限白名单成功");
    }

    /**
     * @Title: doFilter
     * @Description: 请求有效性验证
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
    	String requestURI = request.getRequestURI();
    	String dev = "dev";
    	if (dev.equals(profilesActive)) {
    		if (permitUrl.isSwaggerUrl(requestURI)) {
    			filterChain.doFilter(servletRequest, servletResponse);
    			return;
    		}
    	}

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * @Title: destroy
     * @Description: noo
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }
}
