package com.github.rogerli.config.init;

import com.github.rogerli.system.conf.model.ConfQuery;
import com.github.rogerli.system.conf.service.ConfService;
import com.github.rogerli.system.dic.model.DictQuery;
import com.github.rogerli.system.dic.service.DictService;
import com.github.rogerli.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author roger.li
 * @since 2019/6/6
 */
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    private ConfService confService;
    @Autowired
    private DictService dictService;
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public void run(String... args) throws Exception {
        logger.info("server startup runner");

        // 加载所有系统配置到redis
        redisUtils.set("confs", confService.findList(new ConfQuery().setValid(1)));
        // 加载所有字典到redis
        redisUtils.set("dicts", dictService.findList(new DictQuery().setValid(1)));
    }


}
