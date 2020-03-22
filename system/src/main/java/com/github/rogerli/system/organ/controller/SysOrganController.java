package com.github.rogerli.system.organ.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.system.organ.entity.Organ;
import com.github.rogerli.system.organ.model.OrganQuery;
import com.github.rogerli.system.organ.service.OrganService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@RestController
@RequestMapping(SysOrganController.CONTENT_PATH)
@Api(tags = "SYS系统单位管理")
public class SysOrganController extends AbstractRestController<Organ, String> {

    public static final String CONTENT_PATH = "sys/organ";

    @Autowired
    private OrganService organService;

    @Autowired
    private LoginService loginService;

    @Override
    protected ServiceInterface<Organ, String> getService() {
        return this.organService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 删除单位
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "删除单位",
            value = {"/delete"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除单位")
    public Result delete(@ApiParam("删除单位参数") @RequestBody Organ entity) {
        log.debug("rest path:" + getContentPath() + "/delete:", entity.toString());
        Assert.hasText(entity.getId(), MessageUtils.get("organ.notExists"));

        // 根据单位查询人员
        OrganQuery query = new OrganQuery();
        query.setId(entity.getId());
        List<Login> list = loginService.findList(query);

        // 如果用户数不为0不能删除
        if (list != null && list.size() > 0) {
            return new Result().error(MessageUtils.get("organ.used.error"));
        }

        return super.delete(entity);
    }

    /**
     * 添加单位
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加单位",
            value = {"/add"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("添加单位")
    public Result add(@ApiParam("添加单位参数") @RequestBody Organ entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.add(entity, bindingResult);
    }

    /**
     * 保存单位
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存单位",
            value = {"/save"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("保存单位")
    public Result save(@ApiParam("保存单位参数") @RequestBody Organ entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(entity, bindingResult);
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
    public Result page(@ApiParam("查询参数") @RequestBody OrganQuery query) {
        return super.page(query);
    }

    /**
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
    public Result select(@ApiParam("查询参数") @RequestBody OrganQuery query) {
        return super.select(query);
    }

}
