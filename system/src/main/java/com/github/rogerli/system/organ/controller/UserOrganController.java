package com.github.rogerli.system.organ.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.organ.entity.Organ;
import com.github.rogerli.system.organ.model.OrganData;
import com.github.rogerli.system.organ.model.OrganQuery;
import com.github.rogerli.system.organ.service.OrganService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@RestController
@RequestMapping(UserOrganController.CONTENT_PATH)
@Api(tags = "USER单位")
public class UserOrganController extends AbstractRestController<Organ, String> {

    public static final String CONTENT_PATH = "user/organ";

    @Autowired
    private OrganService organService;

    @Override
    protected ServiceInterface<Organ, String> getService() {
        return this.organService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 查询级联单位
     *
     * @return
     */
    @RequestMapping(
            name = "查询级联单位",
            value = {"/cascade"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("查询级联单位")
    public Result findCascade(@ApiParam("目录参数对象") @RequestBody OrganQuery query) {
        log.debug("rest path:" + getContentPath() + "/cascade");

        List<OrganData> list = organService.findCascade(query);

        return new Result().ok(MessageUtils.get("find.ok"), null, list);
    }

    /**
     * 查询单个单位
     *
     * @param id
     * @return
     */
    @RequestMapping(
            name = "查询单个单位",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("查询单个单位")
    public Result find(@ApiParam("单位id") @RequestParam String id) {
        return super.find(id);
    }

    /**
     * 查询下级单位
     *
     * @param query
     * @return
     */
    @RequestMapping(
            name = "查询下级单位",
            value = {"/sub"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询下级单位")
    public Result select(@ApiParam("查询参数") @RequestBody OrganQuery query) {
        query.setParentId(query.getId());
        return super.select(query);
    }

}
