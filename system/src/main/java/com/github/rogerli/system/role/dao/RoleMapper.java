package com.github.rogerli.system.role.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.record.annotation.RecordTransient;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.entity.RolePurview;
import com.github.rogerli.system.role.model.RoleData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RoleMapper extends DaoMapper<Role> {

    /**
     * 插入角色与权限多对多关联表
     *
     * @param role
     * @return
     */
    @RecordTransient
    int insertRolePurview(RolePurview role);

    /**
     * 根据角色ID删除所有权限
     *
     * @param id
     * @return
     */
    @RecordTransient
    int deleteRolePurviewByKey(String id);

    /**
     * 根据角色ID查询权限
     *
     * @param id
     * @return
     */
    List<Purview> findPurviewListByRole(String id);

    /**
     * 根据角色ID查询角色（含权限）
     *
     * @param id
     * @return
     */
    RoleData findRoleDataByKey(String id);

    /**
     * 根据权限查询角色清单
     *
     * @param query
     * @return
     */
    List<Role> findRoleListByPurview(Purview query);

}