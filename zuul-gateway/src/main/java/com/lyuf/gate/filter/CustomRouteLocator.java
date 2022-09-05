package com.lyuf.gate.filter;



import com.lyuf.gate.bean.ZuulRouteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author lyuf
 * @Date 2022/8/25
 * @Version 1.0
 */
/**
 * CustomRouteLocator集成SimpleRouteLocator,实现了RefreshableRouteLocator接口
 */
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);

    @Autowired
    private final ZuulProperties properties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        System.out.println(properties.toString());
        logger.info("servletPath:{}", servletPath);
    }

    //刷新路由
    @Override
    public void refresh(){
        doRefresh();
    }

    /**
     * locateRoutes是从SimpleRouteLocator Override过来的，先装载配置文件里面的路由信息，在从数据库里面获取路由信息，
     * 最后都是保存在SimpleRoteLocator 的AtomicReference<map> routes属性中，注意routes是类型
     */
    //返回路由
    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        System.out.println(new Date().toLocaleString());
        //从bootstrap.yml中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            System.out.println(path);
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<ZuulRouteVO> results = jdbcTemplate.query("select * from zuul_route where enabled = true ", new
                BeanPropertyRowMapper<>(ZuulRouteVO.class));
        for (ZuulRouteVO zuulRouteVO : results) {
            if (StringUtils.isEmpty(zuulRouteVO.getPath()) ) {
                continue;
            }
            if (StringUtils.isEmpty(zuulRouteVO.getServiceId()) && StringUtils.isEmpty(zuulRouteVO.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(zuulRouteVO, zuulRoute);
            } catch (Exception e) {
                logger.error("=============load zuul route info from db with error==============", e);
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

}
