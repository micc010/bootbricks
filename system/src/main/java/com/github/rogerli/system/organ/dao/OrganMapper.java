package com.github.rogerli.system.organ.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.organ.entity.Organ;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface OrganMapper extends DaoMapper<Organ> {

}