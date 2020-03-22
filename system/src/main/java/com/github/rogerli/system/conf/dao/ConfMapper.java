package com.github.rogerli.system.conf.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.conf.entity.Conf;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfMapper extends DaoMapper<Conf> {

}
