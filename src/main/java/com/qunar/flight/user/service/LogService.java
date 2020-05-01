package com.qunar.flight.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    private static ExecutorService poll = new ThreadPoolExecutor(10, 10, 20, TimeUnit.MINUTES
            , new ArrayBlockingQueue(20)
            , new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (null != r && r instanceof FutureTask) {
                ((FutureTask) r).cancel(true);
            }
            LOGGER.error("work is canceled ", r);
        }
    });

    static {

    }
    public void doLog(int count) {
        poll.submit(() -> {
           LOGGER.info("第{}次执行", count);
        });
    }

}
