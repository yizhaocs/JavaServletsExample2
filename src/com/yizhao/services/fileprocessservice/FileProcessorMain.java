package com.yizhao.services.fileprocessservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yzhao on 10/17/16.
 */
public class FileProcessorMain {
    private ExecutorService validatorThreadPool = null;

    public FileProcessorMain(){

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("validator-%d")
                .daemon(true)
                .priority(Thread.NORM_PRIORITY - 1)
                .build();
        validatorThreadPool = Executors.newSingleThreadExecutor(factory);
    }
}
