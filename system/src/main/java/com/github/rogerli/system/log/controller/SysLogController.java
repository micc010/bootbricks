package com.github.rogerli.system.log.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.dic.model.DictQuery;
import com.github.rogerli.system.log.entity.Log;
import com.github.rogerli.system.log.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(SysLogController.CONTENT_PATH)
@Api(tags = "SYS系统日志管理")
public class SysLogController extends AbstractRestController<Log, String> {

    public static final String CONTENT_PATH = "sys/log";

    @Autowired
    private LogService logService;

    @Override
    protected ServiceInterface getService() {
        return logService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 查询分页
     *
     * @param query
     * @return
     */
    @RequestMapping(
            name = "查询分页",
            value = {"/page"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询分页")
    public Result page(@ApiParam("查询参数") @RequestBody DictQuery query) {
        return super.page(query);
    }

    /**
     * 查询不分页
     *
     * @param query
     * @return
     */
    @RequestMapping(
            name = "查询不分页",
            value = {"/select"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询不分页")
    public Result select(@ApiParam("查询参数") @RequestBody DictQuery query) {
        return super.select(query);
    }

}
