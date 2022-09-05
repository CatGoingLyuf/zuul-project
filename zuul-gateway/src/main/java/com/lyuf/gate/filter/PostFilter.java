package com.lyuf.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @Author lyuf
 * @Date 2022/8/18 13:36
 * @Version 1.0
 */
@Component
public class PostFilter extends ZuulFilter {

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
        return "post";
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
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        return null;
    }
}
