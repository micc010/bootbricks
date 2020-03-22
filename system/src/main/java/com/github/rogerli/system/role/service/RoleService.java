/**
 * @文件名称： LoginService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/1
 * @作者 ： Roger
 */
package com.github.rogerli.system.role.service;

import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.dao.RoleMapper;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.entity.RolePurview;
import com.github.rogerli.system.role.model.RoleData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Roger
 * @description
 * @create 2016/12/1 0:47
 */
@Slf4j
@Service
public class RoleService extends AbstractService<RoleMapper, Role, String> {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleMapper getMapper() {
        return roleMapper;
    }

    /**
     * 更新角色对应权限
     *
     * @param entity
     */
    private void updatePurview(RoleData entity) {
        log.debug("Update Purview:", entity.toString());
        getMapper().deleteRolePurviewByKey(entity.getId());
        entity.getPurviewList().stream().forEach(purview -> {
            RolePurview rolePurview = new RolePurview();
            rolePurview.setRoleId(entity.getId());
            rolePurview.setPurviewId(purview.getId());
            getMapper().insertRolePurview(rolePurview);
        });
    }

    /**
     * 根据角色名验证角色是否存在
     *
     * @param role
     * @return
     */
    public boolean validRoleByName(String role) {
        log.debug("Valid Role By Name:", role);
        Role entity = getMapper().selectOne(new Role().setRole(role));
        if (entity == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新增角色
     *
     * @param entity
     * @return
     */
    public RoleData insertRole(RoleData entity) {
        log.debug("Insert Role:", entity.toString());
        Assert.notNull(entity.getPurviewList(), "请为角色选择权限！");
        Assert.isTrue(validRoleByName(entity.getRole()), "已有同名角色，请重新输入角色！");

        entity.setValid(1);
        getMapper().insert(entity);

        updatePurview(entity);

        return entity;
    }

    /**
     * 保存角色
     *
     * @param entity
     * @return
     */
    public RoleData saveRole(RoleData entity) {
        log.debug("Save Role:", entity.toString());
        Role role = findByKey(entity.getId());
        Assert.notNull(role, "角色不存在，请重新选择角色进行保存！");
        Assert.isTrue(entity.getRole().equals(role.getRole()), "不允许修改角色名称！");

        getMapper().updateByPrimaryKey(entity);

        updatePurview(entity);

        return entity;
    }

    /**
     * 删除角色
     *
     * @param entity
     * @return
     */
    public Role deleteRole(Role entity) {
        log.debug("Delete Role:", entity.toString());
        Role role = findByKey(entity.getId());
        Assert.notNull(role, "角色不存在，请重新选择角色进行删除！");

        getMapper().deleteByPrimaryKey(role);
        getMapper().deleteRolePurviewByKey(entity.getId());

        return entity;
    }

    /**
     * 根据角色ID查询权限
     *
     * @param id
     * @return
     */
    public List<Purview> findPurviewList(String id) {
        log.debug("Find Purview List By Role:", id);
        return getMapper().findPurviewListByRole(id);
    }

    /**
     * 根据角色ID查询角色（含权限）
     *
     * @param id
     * @return
     */
    public RoleData findRoleAndPurview(String id) {
        log.debug("Find Role Data By Key:", id);
        return getMapper().findRoleDataByKey(id);
    }

    /**
     * 根据权限查询角色清单
     *
     * @param query
     * @return
     */
    public List<Role> findRoleListByPurview(Purview query) {
        log.debug("Find Role List By Purview:", query.toString());
        return getMapper().findRoleListByPurview(query);
    }

}
