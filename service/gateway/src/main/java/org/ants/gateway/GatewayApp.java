package org.ants.gateway;

import org.ants.gateway.entity.PermitUrlEntity;
import org.ants.gateway.filter.ErrorFilter;
import org.ants.gateway.filter.PermitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableZuulProxy
@ServletComponentScan
/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月17日上午8:41:07
 */
public class GatewayApp {
	@Autowired
    private PermitUrlEntity permitUrl;
	
	public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
	@Bean
	public PermitFilter permitFilter(RouteLocator routeLocator) {
		permitUrl.init();
	    return new PermitFilter(routeLocator, permitUrl);
	}
	@Bean
	public ErrorFilter errorFilter() {
	    return new ErrorFilter();
	}
}
