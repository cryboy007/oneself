package com.znsd.oneself.model.dto;

import com.znsd.oneself.util.merger_request.BaseMergerResp;
import lombok.Data;

import java.util.function.Supplier;

/**
 * @ClassName PersonResp
 * @Author tao.he
 * @Since 2022/7/25 11:20
 */
@Data
public class PersonResp extends BaseMergerResp {
    private Long id;
    private String username;

    @Override
    protected Supplier<? extends Comparable> requestId() {
        return () -> id;
    }
}
