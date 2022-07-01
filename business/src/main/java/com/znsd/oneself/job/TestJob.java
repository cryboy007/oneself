package com.znsd.oneself.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestJob
 * @Author tao.he
 * @Since 2022/7/1 10:15
 */
@Slf4j
@Component
public class TestJob {
    @XxlJob("test")
    public void test() {
        System.out.println("test job ----->>");
    }
}
