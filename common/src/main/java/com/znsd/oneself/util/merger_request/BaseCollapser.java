package com.znsd.oneself.util.merger_request;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName BaseCollapser
 * @Author tao.he
 * @Since 2022/7/25 10:32
 */
@Slf4j
public abstract class BaseCollapser<T extends BaseMergerResp,R extends BaseMergerReq<T>> {
    /** 积攒请求的阻塞队列 */
    private LinkedBlockingDeque<R> requestQueue = new LinkedBlockingDeque<>();
    /** 线程池数量 */
    private int threadNum = 1;
    /** 定时间隔时长 */
    private long period = 5;

    @PostConstruct
    public void init() {
        final NamedThreadFactory threadFactory = new NamedThreadFactory("合并请求线程池", true);
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(threadNum, threadFactory);
        // 每5秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(new ExecuteMethod(), 0, period, TimeUnit.SECONDS);
        log.debug("合并请求线程启动成功");
    }

    public T getResult(R r) throws InterruptedException, ExecutionException {
        CompletableFuture<T> completedFuture = new CompletableFuture<>();
        r.setResult(completedFuture);
        requestQueue.add(r);
        return r.getResult().get();
    }

    public class ExecuteMethod implements Runnable {

        @Override
        public void run() {
            List<R> requestQueueTmp = new ArrayList<>();
            // 把出请求层放入的消息queue的元素取出来
            int size = requestQueue.size();
            // 存放批量查询的入参
            List<Comparable> requestParams = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                final R request = requestQueue.poll();
                if (Objects.nonNull(request)) {
                    requestQueueTmp.add(request);
                    Comparable comparator = request.param().get();
                    requestParams.add(comparator);
                }
            }

            if (!requestParams.isEmpty()) {
                try {
                    List<T> response = baseQuery(requestParams);
                    Map<Comparable, T> collect = response.stream().collect(
                            Collectors.toMap(detail -> detail.requestId().get(), Function.identity(), (key1, key2) -> key2));
                    // 通知请求的线程
                    for (R request : requestQueueTmp) {
                        request.getResult().complete(collect.get(request.param().get()));
                    }

                } catch (Exception e) {
                    // 通知请求的线程-异常
                    requestQueueTmp.forEach(request -> request.getResult().obtrudeException(e));
                    throw e;
                }
            }
        }
    }

    protected abstract List<T> baseQuery(List<? super Comparable> params) ;
}
