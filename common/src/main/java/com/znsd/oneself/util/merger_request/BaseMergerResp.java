package com.znsd.oneself.util.merger_request;

import lombok.Data;

import java.util.function.Supplier;

/**
 * @ClassName BaseMergerResp
 * @Author tao.he
 * @Since 2022/7/25 11:09
 */
@Data
public abstract class BaseMergerResp {
    /** 唯一键*/
     protected abstract Supplier<? extends Comparable> requestId();
}
