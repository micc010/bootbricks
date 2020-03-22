/**
 * @文件名称： RoleController.java
 * @文件描述：
 * @版权所有：(C)2017-2028
 * @公司：
 * @完成日期: 2017/1/24
 * @作者 ： Roger
 */
package com.github.rogerli.system.role.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import com.github.rogerli.system.role.model.RoleData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

/**
 * @author Roger
 * @create 2017/1/24 14:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestSystemApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public class SysRoleControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //    @Ignore
    @Test
    public void testValid() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Role entity = new Role();
        entity.setRole("Admin");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/role/valid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0,'data':1}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testDelete() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Role entity = new Role();
        entity.setId("4");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/role/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testAddRole() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
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
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/role/addrole")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testSave() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
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
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/role/saverole")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes("UTF-8")))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testFind() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,
                "/sys/role/find")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testFindRole() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET,
                "/sys/role/findrole")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
