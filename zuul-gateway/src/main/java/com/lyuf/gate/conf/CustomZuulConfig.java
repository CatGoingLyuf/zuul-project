package com.lyuf.gate.conf;

import com.lyuf.gate.filter.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author lyuf
 * @Date 2022/8/25
 * @Version 1.0
 */

@Configuration
public class CustomZuulConfig {
   @Autowired
   ZuulProperties zuulProperties;

   @Autowired
   ServerProperties server;

   @Autowired
   JdbcTemplate jdbcTemplate;

    /**
     * CustomerRouteLocator 去数据库获取路由配置信息，需要一个JdbcTemplate Bean。
     * this.zuulProperties 就是配置文件里面的路由配置，应该是网关服务启动时自动就获取过来的。
     */
   @Bean
   public CustomRouteLocator routeLocator() {
       CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServletPath(), this.zuulProperties);
       routeLocator.setJdbcTemplate(jdbcTemplate);
       return routeLocator;
   }
}
