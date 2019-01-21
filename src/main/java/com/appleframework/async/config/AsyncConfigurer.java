package com.appleframework.async.config;


import com.appleframework.async.pool.RunnableAround;


/**
 * @author woter
 * @date 2017-12-4 下午12:32:40
 */
public interface AsyncConfigurer {

    void configureExecutorConfiguration(com.appleframework.async.config.ExecutorConfiguration configuration);

    void configureThreadPool(com.appleframework.async.config.ThreadPoolConfiguration configuration);

    RunnableAround getRunnableAround();

}