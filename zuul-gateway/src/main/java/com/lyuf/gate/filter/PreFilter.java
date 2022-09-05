package com.lyuf.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author lyuf
 * @Date 2022/8/18 13:36
 * @Version 1.0
 */
@Component
public class PreFilter extends ZuulFilter {

    /**
     * 过滤器类型，前置过滤器
     * @return "pre"
     * pre：请求在路由之前执行。
     * routing：在路由请求时调用。
     * post：在routing和error过滤器之后调用。
     * error：处理请求时发生错误调用
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     * @return num
     * 通过返回的int值来定义过滤器的执行顺序，数字越小优先级越高。
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 该过滤器是否生效
     * @return boole
     * 返回一个Boolean值，判断该过滤器是否需要执行。返回true执行，返回false不执行
     */
    @Override
    public boolean shouldFilter() {

        return true;
    }

    /**
     * 执行校验逻辑
     * @return null
     * @throws ZuulException
     * 过滤器的具体业务逻辑
     */
    @Override
    public Object run() throws ZuulException {
        // 获取zuul提供的上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 从上下文对象中获取请求对象
        HttpServletRequest request = currentContext.getRequest();
        // 请求对象中获取token
        String token = request.getHeader("token");
        // 校验token信息
        if (StringUtils.isBlank(token)) {
            // 这个请求最终不会被zuul转发到后端服务器
            // 但是如果当前Filter后面还存在其他Filter,那么其他Filter仍然会被调用到
            currentContext.setSendZuulResponse(false);
            // 设置响应状态码，401
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            // 设置响应信息
            currentContext.setResponseBody("{\"status\":\"401\", \"text\":\"request error!\",\"message\":\"token invalid!\"}");
        }
        // 校验通过，把登陆信息放入上下文信息，继续向后执行
        currentContext.set("token","lyuf");
        return null;
    }
}
