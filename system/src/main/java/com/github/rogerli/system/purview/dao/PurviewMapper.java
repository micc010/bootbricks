package com.github.rogerli.system.purview.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.purview.entity.Purview;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface PurviewMapper extends DaoMapper<Purview> {

}