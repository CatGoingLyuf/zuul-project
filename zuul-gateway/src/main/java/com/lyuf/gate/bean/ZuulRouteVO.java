package com.lyuf.gate.bean;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class ZuulRouteVO {
    /**
     * 路由的 ID（默认与它的 map key 相同）
     */
    private String id;

    /**
     * The path (pattern) for the route, e.g. /foo/**.
     */
    private String path;

    /**
     * The service ID (if any) to map to this route. You can specify a physical URL or a service, but not both.
     * 映射到此路由的服务 ID（如果有）。您可以指定物理 URL 或服务，但不能同时指定两者。
     */
    private String serviceId;

    /**
     * A full physical URL to map to the route. An alternative is to use a service ID and service discovery to find the physical address.
     * 映射到路由的完整物理 URL。另一种方法是使用服务 ID 和服务发现来查找物理地址。
     */
    private String url;

    /**
     * Flag to determine whether the prefix for this route (the path, minus pattern patcher) should be stripped before forwarding.
     * 用于确定是否应在转发之前剥离此路由的前缀（路径、减模式修补程序）的标志。
     */
    private boolean stripPrefix = true;

    /**
     * Flag to indicate that this route should be retryable (if supported). Generally retry requires a service ID and ribbon.
     * 指示此路由应可重试的标志（如果支持）。通常重试需要服务 ID 和功能区。
     */
    private Boolean retryable;

    private Boolean enabled;
}