package com.znsd.oneself.service;

import com.google.common.collect.Maps;
import com.znsd.oneself.model.Person;
import com.znsd.oneself.model.dto.PersonReq;
import com.znsd.oneself.model.dto.PersonResp;
import com.znsd.oneself.util.merger_request.BaseCollapser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Slf4j
public class PersonService  extends BaseCollapser<PersonReq, PersonResp,Long> {
    Map<Long,PersonResp> tempData = Maps.newHashMap();
    public PersonService() {
        for (long i = 1; i < 11; i++) {
            PersonResp resp = new PersonResp();
            resp.setOnlyKey(i);
            resp.setUsername("name:"+i);
            tempData.put(i,resp);
        }
    }


    public PersonResp getById(Long id) throws ExecutionException, InterruptedException {
        final PersonReq req = new PersonReq();
        req.setFirst(id);
        return super.getResult(req);
    }



    public List<PersonResp> batchQuery(List<Long> ids) {
        return new ArrayList<>(tempData.values());
    }

    @Override
    public List<PersonResp> baseQuery(List<Long> params) {
        log.debug("收集到的id{}",params);
        return this.batchQuery(params);
    }
}
