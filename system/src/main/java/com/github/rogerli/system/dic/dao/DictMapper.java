package com.github.rogerli.system.dic.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.dic.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper extends DaoMapper<Dict> {

}
