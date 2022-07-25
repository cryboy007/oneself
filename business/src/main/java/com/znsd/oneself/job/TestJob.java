package com.znsd.oneself.job;

import com.xxl.job.core.context.XxlJobHelper;
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
        XxlJobHelper.getJobParam();
        final int shardIndex = XxlJobHelper.getShardIndex();
        final int shardTotal = XxlJobHelper.getShardTotal();
        final int i = shardIndex / shardTotal;
        System.out.println("test job ----->>");
    }
}
