package org.ants.rbacs;

import org.ants.rbacs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class RbacApplicationRunner implements ApplicationRunner {
	@Autowired
	private RoleService roleService;
	/**
	 * @Title: run
	 * @Description: 应用启动后自定义初始化
	 * @param args
	 * @throws Exception
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		roleService.initReservedRoles();
	}
}
