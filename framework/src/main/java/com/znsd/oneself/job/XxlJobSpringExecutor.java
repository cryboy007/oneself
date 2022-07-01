package com.znsd.oneself.job;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.handler.IJobHandler;
import com.znsd.oneself.job.annotation.JobHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * xxl-job executor (for spring)
 *
 * @author xuxueli 2018-11-01 09:24:52
 */
@Component
public class XxlJobSpringExecutor extends XxlJobExecutor implements ApplicationContextAware {

	private final Log log = LogFactory.getLog(XxlJobSpringExecutor.class);
    @Override
    public void start() throws Exception {

        // init JobHandler Repository
        initJobHandlerRepository(applicationContext);

        // refresh GlueFactory
        GlueFactory.refreshInstance(1);


        // super start
        super.start();
    }

    private void initJobHandlerRepository(ApplicationContext applicationContext){
        if (applicationContext == null) {
            return;
        }

        //init ip  refer to nacos
        log.info("*****==== XxlRpcSpringProviderFactory fetch IP : ");
    	//设置ip
//    	InetUtils inetUtils = applicationContext.getBean(InetUtils.class);
//        String ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
//        super.setIp(ip);
//        log.info("***** XxlRpcSpringProviderFactory fetch IP : "+ip);
        // init job handler action
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);

        if (serviceBeanMap.size() > 0) {
            for (Object serviceBean : serviceBeanMap.values()) {
                if (serviceBean instanceof IJobHandler){
                    String name = serviceBean.getClass().getAnnotation(JobHandler.class).value();
                    IJobHandler handler = (IJobHandler) serviceBean;
                    if (loadJobHandler(name) != null) {
                        throw new RuntimeException("xxl-job jobhandler naming conflicts.");
                    }
                    registJobHandler(name, handler);
                }
            }
        }
    }

    // ---------------------- applicationContext ----------------------
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        XxlJobSpringExecutor.applicationContext = applicationContext;
        this.initJobHandlerRepository(applicationContext);
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
