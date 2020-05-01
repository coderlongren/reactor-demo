package com.qunar.flight.user.util;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class Logback {
    static Logger logger1 = LoggerFactory.getLogger(Logback.class);

    public static void main(String[] args) throws InterruptedException {
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");
        logger1.trace("trace");
        logger1.info("info");
        logger1.error("error");

        // 设置 logger 的级别为 INFO
        logger.setLevel(Level.INFO);

        // 这条日志可以打印，因为 WARN >= INFO
        logger.warn("警告信息");
        // 这条日志不会打印，因为 DEBUG < INFO
        logger.debug("调试信息");
        // "com.foo.bar" 会继承 "com.foo" 的有效级别
        Logger barLogger = LoggerFactory.getLogger("com.foo.bar");
        // 这条日志会打印，因为 INFO >= INFO
        barLogger.info("子级信息 {}", System.currentTimeMillis());
        // 这条日志不会打印，因为 DEBUG < INFO
        barLogger.debug("子级调试信息");
//        testRollingRule();
    }
    private static void testRollingRule() throws InterruptedException {
        for (;;) {
            logger1.info("打印日志 time = {}", System.currentTimeMillis());
            Thread.sleep(1);
        }
    }
}
