package com.gwg.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/jdbc.properties")
public class MyDbConfig implements ConfigurationCustomizer {

    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.driver}")
    private String driver;

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setUrl(this.url);
        dataSource.setDriverClassName(this.driver);
        return dataSource;
    }

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        configuration.setMapUnderscoreToCamelCase(true);
    }
}
