package com.github.rogerli.system.record.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.record.entity.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends DaoMapper<Record> {

}
