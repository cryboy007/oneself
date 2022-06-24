package com.znsd.oneself.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPoolConfig
 * @Author tao.he
 * @Since 2022/6/15 9:29
 */
@Configuration
@Slf4j
@EnableAsync
public class SpringThreadPoolConfig {
    /**
     * Spring 默认线程池
     */
    @Bean("asyncServiceExecutor")
    public Executor asyncServiceExecutor(ThreadPoolConfig config) {
        int asyncMaxPoolSize = config.getAsyncMaxPoolSize();
        log.info("默认线程池-最大线程数: {}", asyncMaxPoolSize);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(asyncMaxPoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(asyncMaxPoolSize);
        // 配置队列大小
        executor.setQueueCapacity(1000);
        // 设置默认线程名称
        executor.setThreadNamePrefix("asyncService@");
        // 执行初始化
        executor.initialize();

        return executor;
    }


    @Bean("defaultJDKExecutor")
    public ThreadPoolExecutor threadPoolExecutor (ThreadPoolConfig config) {
        return new ThreadPoolExecutor(5,
                config.getAsyncMaxPoolSize(),
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(true),
                new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
