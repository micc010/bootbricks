package com.github.rogerli.system.record.service;

import com.github.rogerli.system.record.dao.RecordMapper;
import com.github.rogerli.system.record.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService extends AbstractService<RecordMapper, Record, Long> {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    protected RecordMapper getMapper() {
        return recordMapper;
    }
}
