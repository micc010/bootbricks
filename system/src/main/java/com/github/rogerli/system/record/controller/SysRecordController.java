package com.github.rogerli.system.record.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.dic.model.DictQuery;
import com.github.rogerli.system.record.entity.Record;
import com.github.rogerli.system.record.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(SysRecordController.CONTENT_PATH)
@Api(tags = "SYS操作记录管理")
public class SysRecordController extends AbstractRestController<Record, String> {

    public static final String CONTENT_PATH = "sys/record";

    @Autowired
    private RecordService recordService;

    @Override
    protected ServiceInterface getService() {
        return recordService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 根据id查询修改记录
     *
     * @param id
     * @return
     */
    @RequestMapping(
            name = "根据id查询修改记录",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("根据id查询修改记录")
    public Result find(@ApiParam("修改记录id") @RequestParam String id) {
        return super.find(id);
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
