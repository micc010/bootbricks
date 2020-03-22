/**
 * @文件名称： LoginService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/1
 * @作者 ： Roger
 */
package com.github.rogerli.system.login.service;

import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.entity.LoginRole;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginPassword;
import com.github.rogerli.system.login.model.LoginQuery;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 用户service
 *
 * @author roger.li
 * @since 2019/5/20
 */
@Slf4j
@Service
public class LoginService implements CURD<LoginMapper, Login, String> {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public LoginMapper getMapper() {
        return loginMapper;
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    public Login findByUsername(String userName) {
        log.debug("Find Login By Username:", userName);
        return getMapper().selectOne(new Login().setUsername(userName));
    }

    /**
     * 根据用户名查询用户及相应角色
     *
     * @param userName
     * @return
     */
    public LoginData findLoginDataByName(String userName) {
        log.debug("Find Login Data By Name:", userName);
        return getMapper().findLoginDataByName(userName);
    }

    /**
     * 根据用户ID查询角色清单
     *
     * @param id
     * @return
     */
    public List<Role> findRoleListByLogin(String id) {
        log.debug("Find Role List By Login:", id);
        return getMapper().findRoleListByLogin(id);
    }

    /**
     * @param roleId
     * @return
     */
    public List<Login> findLoginListByRole(String roleId) {
        log.debug("Find Login List By Role:", roleId);
        return getMapper().findLoginListByRole(roleId);
    }

    /**
     * 根据用户ID查询权限清单
     *
     * @param id
     * @return
     */
    public List<Purview> findPurviewListByLogin(String id) {
        log.debug("Find Purview List By Login:", id);
        return getMapper().findPurviewListByLogin(id);
    }

    /**
     * 根据用户ID查询用户（含角色）
     *
     * @param id
     * @return
     */
    public LoginData findLoginDataByKey(String id) {
        log.debug("Find Login Data By Key:", id);
        return getMapper().findLoginDataByKey(id);
    }

    /**
     * 根据用户名验证账号是否存在
     *
     * @param userName
     * @return
     */
    public boolean validLoginByName(String userName) {
        log.debug("Valid Login By Name:", userName);
        Login login = findByUsername(userName);
        if (login == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新增用户
     *
     * @param entity
     * @return
     */
    public Login insertLogin(Login entity) {
        log.debug("Insert Login:", entity.toString());
        Assert.isTrue(validLoginByName(entity.getUsername()), MessageUtils.get("login.exists"));

        entity.setPassword(encoder.encode(entity.getPassword()));
        getMapper().insert(entity);

        return entity;
    }

    /**
     * 保存用户
     *
     * @param entity
     * @return
     */
    public Login saveLogin(Login entity) {
        log.debug("Save Login:", entity.toString());
        Login login = findByKey(entity.getId());
        Assert.notNull(login, MessageUtils.get("login.notExists"));
        Assert.isTrue(entity.getUsername().equals(login.getUsername()), MessageUtils.get("login.notAllowModify"));

        entity.setPassword(encoder.encode(entity.getPassword()));
        getMapper().updateByPrimaryKey(entity);

        return entity;
    }

    /**
     * 删除用户
     *
     * @param entity
     * @return
     */
    public Login deleteLogin(Login entity) {
        log.debug("Delete Login:", entity.toString());

        loginMapper.deleteLoginRoleByKey(entity.getId());

        getMapper().deleteByPrimaryKey(entity.getId());

        return entity;
    }

    /**
     * 根据条件查询用户清单
     *
     * @param query
     * @return
     */
    public List<LoginData> findLoginDataList(LoginQuery query) {
        log.debug("Find Login Data List By Query:", query.toString());
        setPage(query);
        setOrderby(query);
        return loginMapper.findLoginDataList(query);
    }

    /**
     * 为用户角色授权
     *
     * @param entity
     */
    public boolean authorityUser(LoginData entity) {
        log.debug("Authority User:", entity.toString());
        Assert.notNull(entity.getRoleList(), MessageUtils.get("login.roleEmpty"));
        Login login = findByKey(entity.getId());
        Assert.notNull(login, MessageUtils.get("login.notExists"));
        loginMapper.deleteLoginRoleByKey(entity.getId());
        entity.getRoleList().stream().forEach(role -> {
            LoginRole loginRole = new LoginRole();
            loginRole.setLoginId(login.getId());
            loginRole.setRoleId(role.getId());
            loginRole.setUsername(login.getUsername());
            loginMapper.insertLoginRole(loginRole);
        });
        return true;
    }

    /**
     * 修改密码
     *
     * @param loginPassword
     * @return
     */
    public boolean modifyPassword(LoginPassword loginPassword) {
        log.debug("Modify Password:", loginPassword.toString());
        Assert.notNull(loginPassword, MessageUtils.get("login.newPassword"));
        Login entity = findByUsername(loginPassword.getUsername());
        Assert.notNull(entity, MessageUtils.get("login.notExists"));
        if (!encoder.matches(loginPassword.getOldPassword(), entity.getPassword())) {
            return false;
        }
        entity.setPassword(encoder.encode(loginPassword.getPassword()));
        getMapper().updateByPrimaryKeySelective(entity);
        return true;
    }

    /**
     * 重置密码
     *
     * @param username
     * @return
     */
    public boolean resetPassword(String username) {
        log.debug("Reset Password:", username);
        Login entity = findByUsername(username);
        Assert.notNull(entity, MessageUtils.get("login.notExists"));
        entity.setPassword(encoder.encode(Login.DEFAULT_PASSWORD));
        getMapper().updateByPrimaryKeySelective(entity);
        return true;
    }
}
