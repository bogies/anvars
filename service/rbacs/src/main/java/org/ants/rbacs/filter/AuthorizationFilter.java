package org.ants.rbacs.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.entity.Result;
import org.ants.common.constants.ErrorConstants;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.rbacs.entity.HeaderTokenEntity;
import org.ants.rbacs.entity.PermitUrlEntity;
import org.ants.rbacs.service.impl.ResourcesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @ClassName: AuthorizationFilter
 * @Description: 请求合法性验证
 * @author: Jerry
 * @date: 2018年12月12日 下午5:16:25
 */
@Order(0)
@WebFilter(filterName="authorizationFilter")
public class AuthorizationFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);
	private Gson gson = new Gson();
	
	@Value("${spring.profiles.active}")
	private String profilesActive;
	@Autowired
	private PermitUrlEntity permitUrl;
	@Autowired
	private HeaderTokenEntity tokenConfig;
	//@Autowired
	//private UserService userService;
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	permitUrl.init();
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
    	HttpServletRequest req = (HttpServletRequest) servletRequest;
    	String requestURI = req.getRequestURI();
    	String dev = "dev";
    	if (dev.equals(profilesActive)) {
    		if (permitUrl.isSwaggerUrl(requestURI)) {
    			filterChain.doFilter(servletRequest, servletResponse);
    			return;
    		}
    	}
    	if (permitUrl.isPermitAllUrl(requestURI)) {
    		filterChain.doFilter(servletRequest, servletResponse);
			return;
    	}
    	
        String authToken = req.getHeader(RequestHeaderConstants.AUTH_TOKEN);
        CustomRequestEntity customRequest = tokenConfig.parseAuthToken(authToken);
        
        if (!customRequest.isSuccess()) {
        	PrintWriter writer = null;
			OutputStreamWriter osw = null;
			String jsonStr = null;
			try {
				osw = new OutputStreamWriter(servletResponse.getOutputStream() , "UTF-8");
				writer = new PrintWriter(osw, true);
				Result rlt =  new Result(ErrorConstants.SE_AUTH_TOKEN);
				jsonStr = gson.toJson(rlt);
				writer.write(jsonStr);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != writer) {
					writer.close();
				}
				if(null != osw){
					osw.close();
				}
			}
			logger.info(jsonStr);
			return;
        }
        
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req);
        requestWrapper.addString("userId", customRequest.getUserId());
        
        filterChain.doFilter(requestWrapper, servletResponse);
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
