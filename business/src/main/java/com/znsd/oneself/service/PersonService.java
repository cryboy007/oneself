package com.znsd.oneself.service;

import com.google.common.collect.Maps;
import com.znsd.oneself.model.dto.PersonReq;
import com.znsd.oneself.model.dto.PersonResp;
import com.znsd.oneself.util.merger_request.BaseCollapser;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @ClassName PersonService
 * @Author tao.he
 * @Since 2022/7/25 11:18
 */
@Service
public class PersonService  extends BaseCollapser<PersonResp, PersonReq> {
    Map<Long,PersonResp> tempData = Maps.newHashMap();
    public PersonService() {
        for (long i = 1; i < 11; i++) {
            PersonResp resp = new PersonResp();
            resp.setId(i);
            resp.setUsername("name:"+i);
            tempData.put(i,resp);
        }
    }


    public PersonResp getById(Long id) throws ExecutionException, InterruptedException {
        final PersonReq req = new PersonReq();
        req.setId(id);
        return super.getResult(req);
    }

    @Override
    protected List<PersonResp> baseQuery(List<? super Comparable> params) {
        final List<Long> ids = params.stream().mapToLong(item -> (Long) item).boxed().collect(Collectors.toList());
        return this.batchQuery(ids);
    }

    public List<PersonResp> batchQuery(List<Long> ids) {
        return new ArrayList<>(tempData.values());
    }
}
