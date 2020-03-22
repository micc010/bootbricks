/**
 * @文件名称： LoginMapperTest.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/11/30
 * @作者 ： Roger
 */
package com.github.rogerli.system.login.dao;

import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.entity.LoginRole;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginQuery;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class LoginMapperTest {

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        LoginData login = loginMapper.findLoginDataByName("test_1");
        Set<GrantedAuthority> authorities = login.getRoleList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole()))
                .collect(Collectors.toSet());
        BoastUserDetail userDetail = new BoastUserDetail(login.getId(),
                login.getUsername(),
                login.getFullName(),
                login.getPassword(),
                login.getOrganId(),
                login.getOrganName(),
                new Date(),
                authorities);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test_1", encoder.encode(login.getPassword()), authorities);
        token.setDetails(userDetail);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Ignore
    @Test
    public void testFindByUsername() {
        Login login = loginMapper.selectOne(new Login().setUsername("test_1"));
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFindRoleByUsername() {
        LoginData login = loginMapper.findLoginDataByName("test_1");
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFindRoleListByLogin() {
        List<Role> roles = loginMapper.findRoleListByLogin("1"); // test_1
        Assert.notEmpty(roles, "error");
    }

    @Ignore
    @Test
    public void testFindLoginListByRole() {
        List<Login> logins = loginMapper.findLoginListByRole("1");
        Assert.notNull(logins, "error");
    }

    @Ignore
    @Test
    public void testFindLoginPurview() {
        List<Purview> purviews = loginMapper.findPurviewListByLogin("1"); // test_1
        Assert.notEmpty(purviews, "error");
    }

    @Ignore
    @Test
    public void testFindUserRole() {
        LoginData login = loginMapper.findLoginDataByKey("1");// test_1
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFindLoginList() {
        LoginQuery queryLogin = new LoginQuery();
        queryLogin.setUsername("dsfzxgs");
        List<LoginData> login = loginMapper.findLoginDataList(queryLogin);
        Assert.notNull(login, "error");
    }

    //    @Ignore
    @Test
    public void testFindByKey() {
//        Login l = new Login();
//        l.setId("1");
        Login login = loginMapper.selectByPrimaryKey("1");
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testInsertLoginRole() {
        LoginRole entity = new LoginRole();
        entity.setUsername("test_1");
        entity.setRoleId("1");
        entity.setLoginId("test_1");
        int i = loginMapper.insertLoginRole(entity);
        Assert.isTrue(i == 1, "error");
    }

    @Ignore
    @Test
    public void testdDeleteLoginRoleByKey() {
        int j = loginMapper.deleteLoginRoleByKey("1");// test_1
        Assert.isTrue(j > 0, "error");
    }

    @Ignore
    @Test
    public void testdInsert() {
        Login entity = new Login();
        entity.setUsername("test_3");
        entity.setOrganId("1");
        entity.setPassword("11111");
        entity.setLocked(1);
        entity.setFullName("1123131");
        entity.setOrganName("公司");
        int i = loginMapper.insert(entity);
        Assert.isTrue(i > 0, "error");
    }

    @Ignore
    @Test
    public void testdDeleteByKey() {
        Login entity = new Login();
        int i = loginMapper.deleteByPrimaryKey(entity);
        Assert.isTrue(i > 0, "error");
    }
}
