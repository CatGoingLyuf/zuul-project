package com.lyuf.gate.service;

import com.lyuf.gate.filter.CustomRouteLocator;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


/**
 * @Author lyuf
 * @Date 2022/8/25
 * @Version 1.0
 */
@Service
public class RefreshRouteService {

   @Autowired
   ApplicationEventPublisher publisher;

   @Autowired
   RouteLocator routeLocator;

   public final static org.slf4j.Logger logger = LoggerFactory.getLogger(RefreshRouteService.class);

   public void refreshRoute() {
       /**
        * 在发生ContextRefreshedEvent和RoutesRefreshedEvent事件时会执行this.zuulHandlerMapping.setDirty(true);
        * 因此我们如果要主动刷新路由规则，只需要发布一个RoutesRefreshedEvent事件即可
        */
       RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
       // 发布事件
       publisher.publishEvent(routesRefreshedEvent);
       logger.info("刷新了路由规则......");
   }

}
