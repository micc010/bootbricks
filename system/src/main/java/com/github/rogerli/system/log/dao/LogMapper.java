package com.github.rogerli.system.log.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.log.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface LogMapper extends DaoMapper<Log> {

}