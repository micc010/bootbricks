package com.github.rogerli.system.purview.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.purview.model.PurviewData;
import com.github.rogerli.system.purview.model.PurviewQuery;
import com.github.rogerli.system.purview.service.PurviewService;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.service.RoleService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@RestController
@RequestMapping(SysPurviewController.CONTENT_PATH)
@Api(tags = "SYS权限管理")
public class SysPurviewController extends AbstractRestController<Purview, Integer> {

    public static final String CONTENT_PATH = "sys/purview";

    @Autowired
    private PurviewService purviewService;

    @Autowired
    private RoleService roleService;

    @Override
    protected ServiceInterface<Purview, Integer> getService() {
        return this.purviewService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 删除权限
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(
            name = "删除权限",
            value = {"/delete"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除权限")
    public Result delete(@ApiParam("删除权限参数") @RequestBody Purview entity) {
        // 根据权限查询角色
        List<Role> list = roleService.findRoleListByPurview(entity);

        // 如果角色数不为0不能删除
        if (list != null && list.size() > 0) {
            return new Result().error(MessageUtils.get("purview.used.error"));
        }

        return super.delete(entity);
    }

    /**
     * 添加权限
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加权限",
            value = {"/add"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("添加权限")
    public Result add(@ApiParam("添加权限参数") @RequestBody Purview entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.add(entity, bindingResult);
    }

    /**
     * 保存权限
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存权限",
            value = {"/save"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("保存权限")
    public Result save(@ApiParam("保存权限参数") @RequestBody Purview entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(entity, bindingResult);
    }

    /**
     * 查询单个权限
     *
     * @param id
     *
     * @return
     */
    @RequestMapping(
            name = "查询单个权限",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("查询单个权限")
    public Result find(@ApiParam("权限id") @RequestParam Integer id) {
        return super.find(id);
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
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询分页")
    public Result page(@ApiParam("查询参数") @RequestBody PurviewQuery query) {
        return super.page(query);
    }

    /**
     * 查询不分页
     *
     * @param query
     *
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
    public Result select(@ApiParam("查询参数") @RequestBody PurviewQuery query) {
        return super.select(query);
    }



    /**
     * 查询下级权限
     *
     * @param query
     * @return
     */
    @RequestMapping(
            name = "查询下级权限",
            value = {"/sub"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询下级权限")
    public Result sub(@ApiParam("查询参数") @RequestBody PurviewQuery query) {
        query.setParentId(query.getId());
        return super.select(query);
    }

    /**
     * 查询级联权限
     *
     * @return
     */
    @RequestMapping(
            name = "查询级联权限",
            value = {"/cascade"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("查询级联权限")
    public Result findCascade(@ApiParam("权限参数对象") @RequestBody PurviewQuery query) {
        log.debug("rest path:" + getContentPath() + "/cascade");

        List<PurviewData> list = purviewService.findCascade(query);

        return new Result().ok(MessageUtils.get("find.ok"), null, list);
    }

}
