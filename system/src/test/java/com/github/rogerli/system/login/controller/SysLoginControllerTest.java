/**
 * @文件名称： RoleController.java
 * @文件描述：
 * @版权所有：(C)2017-2028
 * @公司：
 * @完成日期: 2017/1/24
 * @作者 ： Roger
 */
package com.github.rogerli.system.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.login.model.BoastUserDetail;
import com.github.rogerli.system.login.model.LoginData;
import com.github.rogerli.system.login.model.LoginPassword;
import com.github.rogerli.system.login.service.LoginService;
import com.github.rogerli.system.role.entity.Role;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Roger
 * @create 2017/1/24 14:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestSystemApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public class SysLoginControllerTest implements ApplicationContextAware {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private LoginService loginService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.wac = (WebApplicationContext) applicationContext;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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

    //    @Ignore
    @Test
    public void testChangepw() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginPassword entity = new LoginPassword();
        entity.setPassword("11112");
        entity.setOldPassword("11111");
//        entity.setUsername("test_1");
        String requestJson = objectMapper.writeValueAsString(entity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/modifypw")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testAuthority() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginData entity = new LoginData();
        entity.setId("1");
        entity.setRoleList(new ArrayList<Role>());
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
//                .andExpect(MockMvcResultMatchers.content().json("{'status':200,'message':'授权成功！','data':" + requestJson + "}", false))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testValid() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setUsername("test_1");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/valid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testDelete() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setId("1");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/del")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testAdd() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setUsername("test_3");
        entity.setOrganId("1");
        entity.setOrganName("公司");
        entity.setPassword("11111");
        entity.setLocked(1);
        entity.setFullName("1123131");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testSave() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setId("1");
        entity.setUsername("test_1");
        entity.setOrganId("1");
        entity.setOrganName("公司");
        entity.setPassword("11112");
        entity.setLocked(0);
        entity.setFullName("daffasf");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testFind() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,
                "/sys/login/find")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testPage() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setLocked(0);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/login/page")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testSelect() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Login entity = new Login();
        entity.setLocked(0);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,
                "/sys/login/select")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
