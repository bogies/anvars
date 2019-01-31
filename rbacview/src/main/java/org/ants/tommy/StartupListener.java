package org.ants.tommy;

import org.ants.common.entity.Result;
import org.ants.tommy.utils.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @ClassName: StartupListener
 * @Description: 网站启动时初始化
 * @author: Jerry
 * @date: 2018年12月20日 下午3:59:50
 */
@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);
			
	private static boolean loaded = false;
	public StartupListener() {
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext parent = event.getApplicationContext().getParent();
		if (null == parent) {
			return;
		}
		
		if (!loaded && parent.getDisplayName().equals("Root WebApplicationContext")) {
			Result rlt = RequestService.getServiceToken();
			if (rlt.isSuccess()) {
				AppConfig.instance().setToken((String)rlt.getData());
			} else {
				LOGGER.error("获取服务Token失败{}", rlt.getMessage());
			}
			loaded = true;
		}
	}
}
