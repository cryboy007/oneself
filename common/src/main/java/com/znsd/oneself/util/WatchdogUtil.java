package com.znsd.oneself.util;

import org.apache.commons.exec.Watchdog;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName WatchdogUtil
 * @Author tao.he
 * @Since 2022/8/3 10:37
 */
public class WatchdogUtil {
    public static void main(String[] args) {
        System.out.println(TimeUnit.SECONDS.toMillis(2));
        /*Watchdog watchdog = new Watchdog(TimeUnit.MILLISECONDS.toSeconds(1));
        final Thread t = Thread.currentThread();
        watchdog.addTimeoutObserver(w -> {
            t.interrupt();
            w.stop();
        });
        watchdog.start();
        watchdog.stop();*/
    }
}
