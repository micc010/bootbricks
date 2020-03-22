package com.github.rogerli.system.role.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.model.RoleData;
import com.github.rogerli.system.role.model.RoleQuery;
import com.github.rogerli.system.role.service.RoleService;
import com.github.rogerli.utils.MessageUtils;
import com.google.common.collect.ImmutableMap;
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
@RequestMapping(SysRoleController.CONTENT_PATH)
@Api(tags = "SYS系统角色管理")
public class SysRoleController extends AbstractRestController<Role, String> {

    public static final String CONTENT_PATH = "sys/role";

    @Autowired
    private RoleService roleService;
    @Autowired
    private LoginService loginService;

    @Override
    protected ServiceInterface<Role, String> getService() {
        return this.roleService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 验证角色是否存在
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "验证角色是否存在",
            value = {"/valid"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("验证角色是否存在")
    public Result valid(@ApiParam("角色标识") @RequestBody Role entity) {
        log.debug("rest path:" + getContentPath() + "/valid:", entity.toString());
        if (!roleService.validRoleByName(entity.getRole())) {
            return new Result().ok(MessageUtils.get("role.exists"), null, 1);
        } else {
            return new Result().error(MessageUtils.get("role.notExists"), null, 0);
        }
    }

    /**
     * 删除角色
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "删除角色",
            value = {"/delete"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除角色")
    public Result delete(@ApiParam("删除角色参数") @RequestBody Role entity) {
        log.debug("rest path:" + getContentPath() + "/delete:", entity.toString());
        // 根据角色查询用户
        List<Login> list = loginService.findLoginListByRole(entity.getId());

        // 如果用户数不为0不能删除
        if (list != null && list.size() > 0) {
            return new Result().error(MessageUtils.get("role.used.error"));
        }

        roleService.deleteRole(entity);

        return new Result().ok(MessageUtils.get("del.ok"), null, entity);
    }

    /**
     * 添加角色
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加角色",
            value = {"/add"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("添加角色")
    public Result add(@ApiParam("添加角色参数") @RequestBody RoleData entity, BindingResult bindingResult) throws IllegalValidateException {
        log.debug("rest path:" + getContentPath() + "/add:", entity.toString());
        ImmutableMap<String, Object> map = Result.bindErrors(bindingResult);

        roleService.insertRole(entity);

        return new Result().ok(MessageUtils.get("add.ok"), map, entity);
    }

    /**
     * 保存角色
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存角色",
            value = {"/save"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("保存角色")
    public Result saveRole(@ApiParam("保存角色参数") @RequestBody RoleData entity, BindingResult bindingResult) throws IllegalValidateException {
        log.debug("rest path:" + getContentPath() + "/save:", entity.toString());
        ImmutableMap<String, Object> map = Result.bindErrors(bindingResult);

        roleService.saveRole(entity);

        return new Result().ok(MessageUtils.get("save.ok"), map, entity);
    }

    /**
     * 查询单个角色及角色对应权限
     *
     * @param id
     * @return
     */
    @RequestMapping(
            name = "查询单个角色及角色对应权限",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("查询单个角色及角色对应权限")
    public Result findRole(@ApiParam("角色id") @RequestParam String id) {
        log.debug("rest path:" + getContentPath() + "/find:", id);

        RoleData data = roleService.findRoleAndPurview(id);

        return new Result().ok(MessageUtils.get("find.ok"), null, data);
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
    public Result page(@ApiParam("查询参数") @RequestBody RoleQuery query) {
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
    public Result select(@ApiParam("查询参数") @RequestBody RoleQuery query) {
        return super.select(query);
    }

}
