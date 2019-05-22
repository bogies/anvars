package org.bogies.rbacs.config;


import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"org.bogies.rbacs.dao.rbac"}, sqlSessionFactoryRef="sqlSessionFactoryBean")
/**
` * @ClassName: DatasourceRbacConfig
 * @Description: 权限控制数据源配置
 * @author: jerry
 * @date: 2018年12月12日 下午2:30:12
 */
public class DatasourceRbacConfig {
	@Primary
    @Bean(name="datasourceRbac")
    @ConfigurationProperties(prefix = "spring.datasource.rbac")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
	@Primary
	@Bean(name="sqlSessionFactoryBean")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 使用log4j2
        org.apache.ibatis.logging.LogFactory.useLog4J2Logging();
        
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/rbac_*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

}
