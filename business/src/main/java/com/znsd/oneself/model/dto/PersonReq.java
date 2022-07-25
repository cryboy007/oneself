package com.znsd.oneself.model.dto;

import com.znsd.oneself.util.merger_request.BaseMergerReq;
import lombok.Data;

import java.util.function.Supplier;

/**
 * @ClassName PersonReq
 * @Author tao.he
 * @Since 2022/7/25 11:23
 */
@Data
public class PersonReq extends BaseMergerReq<PersonResp> {
    private String username;
    private Long id;

    @Override
    protected Supplier<? extends Comparable> param() {
        return () -> id;
    }
}
