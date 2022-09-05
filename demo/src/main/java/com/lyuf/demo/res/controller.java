package com.lyuf.demo.res;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyuf
 * @Date 2022/8/17 16:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class controller {

    @GetMapping("/get")
    public String get(){
        log.info("调用demo get方法");
        return "调用demo get方法";
    }
}
