package com.github.rogerli.system.conf.service;

import com.github.rogerli.system.conf.dao.ConfMapper;
import com.github.rogerli.system.conf.entity.Conf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConfService extends AbstractService<ConfMapper, Conf, String> {

    @Autowired
    private ConfMapper confMapper;

    @Override
    protected ConfMapper getMapper() {
        return confMapper;
    }

    /**
     * 根据编码查询单个系统配置项
     *
     * @param code
     * @return
     */
    public Conf findByCode(String code) {
        log.debug("findByCode:", code);
        return confMapper.selectOne(new Conf().setCode(code));
    }

}
