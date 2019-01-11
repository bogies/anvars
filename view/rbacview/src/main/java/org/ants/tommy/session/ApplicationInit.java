package org.ants.tommy.session;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ApplicationInit
 * @Description: 网站启动时初始化
 * @author: Jerry
 * @date: 2018年12月20日 下午3:59:50
 */
@Service
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {
	public ApplicationInit() {
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// init something
	}
}
