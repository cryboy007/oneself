package com.znsd.oneself.util.merger_request;

import lombok.Data;

/**
 * @ClassName BaseMergerResp
 * @Author tao.he
 * @Since 2022/7/25 11:09
 */
@Data
public  class BaseMergerResp<A> {
    private A onlyKey;
}
