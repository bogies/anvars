package org.ants.tommy.session;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ants.tommy.AppConfig;
import org.ants.tommy.entity.ConfigEntity;
import org.ants.tommy.entity.WebConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor implements HandlerInterceptor {
	@Autowired
	private ConfigEntity configEntity;
	@Autowired
	private WebConfigEntity webConfigEntity;
	
	@PostConstruct
	private void initConfig() {
		AppConfig.instance().setConfig(configEntity);
		AppConfig.instance().setWebConfig(webConfigEntity);
	}
	
	/**
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {

	}

	/**
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		//可重新设置session的时长，防止用户在基本市场内一直在操作，时限到时有未处理结束工作！
		//
	}

	/**
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestUri = request.getRequestURI();
		if (!requestUri.endsWith("html") && !requestUri.endsWith("jsd")) {
			return false;
		}
		
		return true;
	}
}
