package com.znsd.oneself.util.merger_request;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName BaseMergerReq
 * @Author tao.he
 * @Since 2022/7/25 10:30
 */
@Data
public  class BaseMergerReq<A,E>{
    private CompletableFuture<E> result;

    private A first;

}
