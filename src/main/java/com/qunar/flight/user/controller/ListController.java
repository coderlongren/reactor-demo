package com.qunar.flight.user.controller;

import com.qunar.flight.user.annotation.CurrentUser;
import com.qunar.flight.user.pojo.User;
import com.qunar.flight.user.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ListController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListController.class);
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Resource
    private LogService logService;
    @GetMapping("/test")
    public String testMyAnnotationResolver(@CurrentUser("b") User user) {
        long start = System.currentTimeMillis();
//        if (user == null) {
//            LOGGER.error("解析到了NULL值");
//            return "";
//        }
        int count = atomicInteger.incrementAndGet();
        logService.doLog(count);
        LOGGER.info("第{}此请求  时间{}", count, System.currentTimeMillis() - start);
        if (count % 2 == 1) {
            throw new RuntimeException("aa");
        }
        return "";
    }

}
