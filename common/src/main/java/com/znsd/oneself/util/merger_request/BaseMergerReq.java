package com.znsd.oneself.util.merger_request;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @ClassName BaseMergerReq
 * @Author tao.he
 * @Since 2022/7/25 10:30
 */
@Data
public abstract class BaseMergerReq<T>{
    private CompletableFuture<T> result;

    /** 唯一键*/
    protected abstract Supplier<? extends Comparable> param();

}
