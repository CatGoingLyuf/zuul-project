package com.lyuf.demo2.res;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lyuf
 * @Date 2022/8/17 16:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/demoTo")
@Slf4j
public class controller {

    @PostMapping("/post")
    public String get(){
        return "调用demoTo post方法";
    }
}
