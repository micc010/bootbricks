/**
 * @文件名称： LoginMapperTest.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/11/30
 * @作者 ： Roger
 */
package com.github.rogerli.system.login.service;

import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginPassword;
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

import java.util.ArrayList;
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
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
        LoginData login = loginService.findLoginDataByName("test_1");
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
        Login login = loginService.findByUsername("test_1");
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFindRoleByUsername() {
        LoginData login = loginService.findLoginDataByName("test_1");
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFindRoleListByLogin() {
        List<Role> roles = loginService.findRoleListByLogin("1"); // test_1
        Assert.notEmpty(roles, "error");
    }

    @Ignore
    @Test
    public void testFindLoginListByRole() {
        List<Login> logins = loginService.findLoginListByRole("1"); // Admin角色
        Assert.notEmpty(logins, "error");
    }

    @Ignore
    @Test
    public void testFindLoginPurview() {
        List<Purview> purviews = loginService.findPurviewListByLogin("1"); // test_1
        Assert.notEmpty(purviews, "error");
    }

    @Ignore
    @Test
    public void testFindLoginAndRole() {
        LoginData login = loginService.findLoginDataByKey("1"); // test_1
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testsInsertUser() {
        Login entity = new Login();
        entity.setUsername("test_3");
        entity.setOrganId("1");
        entity.setPassword("11111");
        entity.setLocked(1);
        entity.setFullName("1123131");
        entity.setOrganName("公司");
        entity = loginService.insertLogin(entity);
        Assert.notNull(entity, "error");
    }

    @Ignore
    @Test
    public void testsSaveUser() {
        Login entity = new Login();
        entity.setId("1");
        entity.setUsername("test_1");
        entity.setOrganId("1");
        entity.setPassword("11112");
        entity.setLocked(0);
        entity.setFullName("daffasf");
        entity.setOrganName("公司");
        entity = loginService.saveLogin(entity);
        Assert.notNull(entity, "error");
    }

    //    @Ignore
    @Test
    public void testsDeleteUser() {
        Login entity = new Login();
        entity.setId("1");
        entity = loginService.deleteLogin(entity);
        Assert.notNull(entity, "error");
    }

    @Ignore
    @Test
    public void testFindLoginList() {
        LoginQuery queryLogin = new LoginQuery();
        queryLogin.setUsername("test_1");
        List<LoginData> login = loginService.findLoginDataList(queryLogin);
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testFind() {
        Login login = loginService.findByKey("1");
        Assert.notNull(login, "error");
    }

    @Ignore
    @Test
    public void testAuthorityUser() {
        LoginData entity = new LoginData();
        entity.setId("1");
        entity.setRoleList(new ArrayList<Role>());
        loginService.authorityUser(entity);
    }

    @Ignore
    @Test
    public void testValidLoginByName() {
        boolean flag = loginService.validLoginByName("test_1");
        Assert.isTrue(!flag, "error");
    }

    @Ignore
    @Test
    public void testModifyPW() {
        LoginPassword loginPassword = new LoginPassword("test_1", "11112", "11111");
        boolean flag = loginService.modifyPassword(loginPassword);
        Assert.isTrue(flag, "error");
    }

    @Ignore
    @Test
    public void testResetPW() {
        boolean flag = loginService.resetPassword("test_1");
        Assert.isTrue(flag, "error");
    }

    @Ignore
    @Test
    public void testEncode() {
        String code = encoder.encode("11111");
        Assert.isTrue(encoder.matches("11111", code), "error");
    }
}
