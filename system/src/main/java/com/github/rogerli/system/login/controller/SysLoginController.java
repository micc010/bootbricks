package com.github.rogerli.system.login.controller;

import com.github.pagehelper.PageInfo;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginPassword;
import com.github.rogerli.system.login.model.LoginQuery;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.utils.MessageUtils;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 用户controller
 *
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@RestController
@RequestMapping(SysLoginController.CONTENT_PATH)
@Api(tags = "SYS系统用户管理")
@ApiIgnore
public class SysLoginController extends AbstractRestController<Login, String> {

    public static final String CONTENT_PATH = "sys/login";

    @Autowired
    private LoginService loginService;

    @Override
    protected ServiceInterface<Login, String> getService() {
        return this.loginService;
    }


    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 修改密码
     *
     * @param loginPassword
     * @return
     */
    @RequestMapping(
            name = "修改密码",
            value = {"/modify-pw"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("修改密码")
    public Result<Login> modifyPassword(@ApiParam("修改密码参数") @RequestBody LoginPassword loginPassword) {
        log.debug("rest path:" + getContentPath() + "/modify-pw:", loginPassword.toString());
        if (!loginService.modifyPassword(loginPassword)) {
            return new Result().ok(MessageUtils.get("login.pw.error.old"));
        } else {
            return new Result().ok(MessageUtils.get("login.pw.ok"));
        }
    }

    /**
     * 重置密码
     *
     * @param username
     * @return
     */
    @RequestMapping(
            name = "重置密码",
            value = {"/reset-pw"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("重置密码")
    public Result<Login> resetPassword(String username) {
        log.debug("rest path:" + getContentPath() + "/reset-pw:", username);
        if (!loginService.resetPassword(username)) {
            return new Result().ok(MessageUtils.get("login.pw.error.old"));
        } else {
            return new Result().ok(MessageUtils.get("login.pw.ok"));
        }
    }

    /**
     * 授权用户角色
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "用户授权",
            value = {"/auth"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("用户授权")
    public Result<LoginData> auth(@ApiParam("用户授权参数") @RequestBody LoginData entity, BindingResult bindingResult) throws IllegalValidateException {
        log.debug("rest path:" + getContentPath() + "/auth:", entity.toString());
        ImmutableMap<String, Object> valid = Result.bindErrors(bindingResult);

        // 设置角色
        loginService.authorityUser(entity);

        return new Result().ok(MessageUtils.get("login.auth.ok"), valid, entity);
    }

    /**
     * 验证用户是否存在
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "验证用户是否存在",
            value = {"/valid"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("验证用户是否存在")
    public Result<Integer> valid(@ApiParam("用户名") @RequestBody Login entity) {
        log.debug("rest path:" + getContentPath() + "/valid:", entity.toString());

        if (!loginService.validLoginByName(entity.getUsername())) {
            return new Result().ok(MessageUtils.get("login.exists"), null, 1);
        } else {
            return new Result().ok(MessageUtils.get("login.notExists"), null, 0);
        }
    }

    /**
     * 删除用户
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "删除用户",
            value = {"/del"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除用户")
    public Result delete(@ApiParam("删除用户参数") @RequestBody Login entity) {
        log.debug("rest path:" + getContentPath() + "/del:", entity.toString());

        loginService.deleteLogin(entity);

        return new Result().ok(MessageUtils.get("del.ok"));
    }

    /**
     * 添加用户
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加用户",
            value = {"/add"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("添加用户")
    public Result<LoginData> add(@ApiParam("添加用户参数") @RequestBody LoginData entity, BindingResult bindingResult) throws IllegalValidateException {
        log.debug("rest path:" + getContentPath() + "/add:", entity.toString());
        ImmutableMap<String, Object> valid = Result.bindErrors(bindingResult);

        // 添加
        loginService.insertLogin(entity);

        return new Result().ok(MessageUtils.get("add.ok"), valid, entity);
    }

    /**
     * 修改用户
     *
     * @param entity
     * @param bindingResult
     * @return
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存用户",
            value = {"/save"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("保存用户")
    public Result<LoginData> save(@ApiParam("保存用户参数") @RequestBody LoginData entity, BindingResult bindingResult) throws IllegalValidateException {
        log.debug("rest path:" + getContentPath() + "/save:", entity.toString());
        ImmutableMap<String, Object> valid = Result.bindErrors(bindingResult);

        // 保存
        loginService.saveLogin(entity);

        return new Result().ok(MessageUtils.get("save.ok"), valid, entity);
    }

    /**
     * 查询单个用户
     *
     * @param id
     * @return
     */
    @RequestMapping(
            name = "查询单个用户",
            value = {"/find"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    @ApiOperation("查询单个用户")
    public Result<Login> find(@ApiParam("用户id") @RequestParam String id) {
        return super.find(id);
    }

    /**
     * 查询单个用户及角色
     *
     * @param entity
     * @return
     */
    @RequestMapping(
            name = "查询单个用户及角色",
            value = {"/find-login"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    @ApiOperation("查询单个用户及角色")
    public Result<LoginData> findLogin(@ApiParam("用户id") @RequestBody Login entity) {
        log.debug("rest path:" + getContentPath() + "/find-login:", entity.toString());

        LoginData data = loginService.findLoginDataByKey(entity.getId());

        return new Result().ok(MessageUtils.get("find.ok"), null, data);
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
    public Result page(@ApiParam("查询参数") @RequestBody LoginQuery query) {
        log.debug("rest path:" + getContentPath() + "/page:", query.toString());
        List<LoginData> list = loginService.findLoginDataList(query);
        PageInfo info = new PageInfo(list);
        return new Result().ok(MessageUtils.get("list.ok"), null, info);
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
    public Result select(@ApiParam("查询参数") @RequestBody LoginQuery query) {
        log.debug("rest path:" + getContentPath() + "/select:", query.toString());
        List<LoginData> list = loginService.findLoginDataList(query);
        return new Result().ok(MessageUtils.get("list.ok"), null, list);
    }

}
