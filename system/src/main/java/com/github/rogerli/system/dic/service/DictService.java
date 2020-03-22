package com.github.rogerli.system.dic.service;

import com.github.rogerli.system.dic.dao.DictMapper;
import com.github.rogerli.system.dic.entity.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DictService extends AbstractService<DictMapper, Dict, Integer> {

    @Autowired
    private DictMapper dictMapper;

    @Override
    protected DictMapper getMapper() {
        return dictMapper;
    }

    /**
     * 根据类型查询单个系统字典列表
     *
     * @param type
     * @return
     */
    public List<Dict> findByType(String type) {
        log.debug("findByType:", type);
        return dictMapper.select(new Dict().setType(type));
    }

}
