/**
 * @文件名称： LoginMapperTest.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/11/30
 * @作者 ： Roger
 */
package com.github.rogerli.system.role.service;

import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.model.RoleData;
import com.github.rogerli.utils.MessageUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roger
 * @description
 * @create 2016/11/30 19:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestSystemApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@Rollback
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    //    @Ignore
    @Test
    public void testValidRoleByName() {
        Assert.isTrue(!roleService.validRoleByName("Admin"), MessageUtils.get("role.exists"));
    }

    //    @Ignore
    @Test
    public void testInsertRole() {
        RoleData entity = new RoleData();
        entity.setRole("ROLE_TEST");
        entity.setRoleName("测试角色");
        entity.setValid(1);
        entity.setOrganId("1");
        entity.setPurviewList(new ArrayList<Purview>());
        Purview purview = new Purview();
        purview.setId(2);
        purview.setName("管理页面");
        purview.setType("menu");
        purview.setUrl("/projindex_admin.html");
        purview.setValid(1);
        purview.setSortNum(1);
        entity.getPurviewList().add(purview);
        entity = roleService.insertRole(entity);
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testSaveRole() {
        RoleData entity = new RoleData();
        entity.setId("1");
        entity.setRole("Admin");
        entity.setRoleName("超级超级管理员");
        entity.setValid(1);
        entity.setOrganId("1");
        entity.setPurviewList(new ArrayList<Purview>());
        Purview purview = new Purview();
        purview.setId(2);
        purview.setName("管理页面");
        purview.setType("menu");
        purview.setUrl("/projindex_admin.html");
        purview.setValid(1);
        purview.setSortNum(1);
        entity.getPurviewList().add(purview);
        entity = roleService.saveRole(entity);
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testDeleteRole() {
        Role entity = new Role();
        entity.setId("4");
        entity = roleService.deleteRole(entity); // Admin角色
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testFindPurviewList() {
        List<Purview> purviews = roleService.findPurviewList("1"); // Admin角色
        Assert.notEmpty(purviews, "error");
    }

    //    @Ignore
    @Test
    public void testFindRoleAndPurview() {
        RoleData entity = roleService.findRoleAndPurview("1");
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testFindRoleListByPurview() {
        Purview query = new Purview();
        query.setId(2);
        query.setUrl("/projindex_admin.html");
        List<Role> roles = roleService.findRoleListByPurview(query);
        Assert.notEmpty(roles, "error");
    }
}
