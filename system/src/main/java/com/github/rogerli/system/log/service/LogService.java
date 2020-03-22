/**
 * @文件名称： LogService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/2
 * @作者 ： Roger
 */
package com.github.rogerli.system.log.service;

import com.github.rogerli.system.log.dao.LogMapper;
import com.github.rogerli.system.log.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Roger
 * @description
 * @create 2016/12/2 16:49
 */
@Service
public class LogService extends AbstractService<LogMapper, Log, Long> {

    @Autowired
    private LogMapper logMapper;

    @Override
    public LogMapper getMapper() {
        return logMapper;
    }

}
