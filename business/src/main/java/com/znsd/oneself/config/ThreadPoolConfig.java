package com.znsd.oneself.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ThreadPoolConfig
 * @Author tao.he
 * @Since 2022/6/15 9:33
 */
@Configuration
@Data
public class ThreadPoolConfig {

    /**
     * 默认线程池-最大线程数
     */
    @Value("${threadPoolConfig.asyncMaxPoolSize:20}")
    private int asyncMaxPoolSize;
}
