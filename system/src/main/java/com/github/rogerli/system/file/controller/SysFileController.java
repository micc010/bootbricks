package com.github.rogerli.system.file.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.file.entity.File;
import com.github.rogerli.system.file.model.FileQuery;
import com.github.rogerli.system.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author roger.li
 * @since 2019/6/20
 */
@Slf4j
@RestController
@RequestMapping(SysFileController.CONTENT_PATH)
@Api(tags = "SYS文件记录")
public class SysFileController extends AbstractRestController<File, Long> {

    public static final String CONTENT_PATH = "sys/file";
    @Autowired
    private FileService fileService;

    @Override
    protected ServiceInterface getService() {
        return fileService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 查询分页
     *
     * @param query
     *
     * @return
     */
    @RequestMapping(
            name = "查询分页",
            value = {"/page"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("查询分页")
    public Result page(@ApiParam("查询参数") @RequestBody FileQuery query) {
        return super.page(query);
    }

}
