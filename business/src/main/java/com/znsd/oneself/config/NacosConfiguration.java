package com.znsd.oneself.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName NacosConfiguration
 * @Author tao.he
 * @Since 2022/6/15 18:58
 * nacos不支持最新的springBoot2.7 ,使用最基础的方式实现
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${nacos_addr:127.0.0.1:8848}"))
@NacosPropertySource(dataId = "${dataId:oneself-dev}.properties", autoRefreshed = true)
@NacosPropertySource(dataId = "${job.dataId:xxl-job}.properties", autoRefreshed = true)
public class NacosConfiguration {

}