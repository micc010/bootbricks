/**
 * @文件名称： LoginMapperTest.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/11/30
 * @作者 ： Roger
 */
package com.github.rogerli.system.role.dao;

import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.entity.RolePurview;
import com.github.rogerli.system.role.model.RoleData;
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
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    //    @Ignore
    @Test
    public void testInsertRolePurview() {
        RolePurview entity = new RolePurview();
        entity.setRoleId("1");
        entity.setPurviewId(1);
        int i = roleMapper.insertRolePurview(entity);
        Assert.isTrue(i == 1, "error");
    }

    //    @Ignore
    @Test
    public void testDeleteRolePurviewByKey() {
        int j = roleMapper.deleteRolePurviewByKey("1");
        Assert.isTrue(j > 0, "error");
    }

    //    @Ignore
    @Test
    public void testFindByRole() {
        Role role = roleMapper.selectOne(new Role().setRole("Admin"));
        Assert.notNull(role, "error");
    }

    //    @Ignore
    @Test
    public void testFindPurviewList() {
        List<Purview> purviews = roleMapper.findPurviewListByRole("1");
        Assert.notNull(purviews, "error");
    }

    //    @Ignore
    @Test
    public void testFindRoleAndPurview() {
        RoleData entity = roleMapper.findRoleDataByKey("1");
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testFindRoleListByPurview() {
        Purview query = new Purview();
        query.setId(2);
        query.setUrl("/projindex_admin.html");
        List<Role> roles = roleMapper.findRoleListByPurview(query);
        Assert.notNull(roles, "error");
    }

}
