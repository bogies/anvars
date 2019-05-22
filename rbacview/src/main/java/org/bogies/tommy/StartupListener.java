package org.bogies.tommy;

import org.bogies.common.entity.CustomRequestEntity;
import org.bogies.common.entity.Result;
import org.bogies.tommy.utils.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

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
		/*ApplicationContext parent = event.getApplicationContext().getParent();
		if (null == parent) {
			return;
		}
		
		if (!loaded && parent.getDisplayName().equals("Root WebApplicationContext")) {
			CustomRequestEntity customReq = new CustomRequestEntity();
			customReq.setRequestId();
			long startTime = System.currentTimeMillis();
			Result rlt = RequestService.getServiceToken(customReq);
			if (rlt.isSuccess()) {
				AppConfig.instance().setToken((String)rlt.getData());
			}
			customReq.setUptime(System.currentTimeMillis()-startTime);
			LOGGER.info(JSON.toJSONString(customReq));
			
			loaded = true;
		}*/
	}
}
