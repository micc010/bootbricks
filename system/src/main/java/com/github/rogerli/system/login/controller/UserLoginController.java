package com.github.rogerli.system.login.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginPassword;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 *
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@RestController
@RequestMapping(UserLoginController.CONTENT_PATH)
@Api(tags = "USER用户")
public class UserLoginController extends AbstractRestController<Login, String> {

    public static final String CONTENT_PATH = "user/login";

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        loginPassword.setUsername(username);
        if (!loginService.modifyPassword(loginPassword)) {
            return new Result().ok(MessageUtils.get("login.pw.error.old"));
        } else {
            return new Result().ok(MessageUtils.get("login.pw.ok"));
        }
    }

    /**
     * 重置密码
     *
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
    public Result<Login> resetPassword() {
        log.debug("rest path:" + getContentPath() + "/reset-pw");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!loginService.resetPassword(username)) {
            return new Result().ok(MessageUtils.get("login.pw.error.old"));
        } else {
            return new Result().ok(MessageUtils.get("login.pw.ok"));
        }
    }

    /**
     * 查询本用户及角色
     *
     * @return
     */
    @RequestMapping(
            name = "查询本用户及角色",
            value = {"/me"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    @ApiOperation("查询本用户及角色")
    public Result<LoginData> findLogin() {
        log.debug("rest path:" + getContentPath() + "/me");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        LoginData data = loginService.findLoginDataByName(username);

        return new Result().ok(MessageUtils.get("find.ok"), null, data.setPassword(null));
    }

}
