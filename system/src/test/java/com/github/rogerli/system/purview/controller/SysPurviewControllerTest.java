/**
 * @文件名称： RoleController.java
 * @文件描述：
 * @版权所有：(C)2017-2028
 * @公司：
 * @完成日期: 2017/1/24
 * @作者 ： Roger
 */
package com.github.rogerli.system.purview.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.organ.entity.Organ;
import com.github.rogerli.system.purview.entity.Purview;
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

/**
 * @author Roger
 * @create 2017/1/24 14:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestSystemApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
public class SysPurviewControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    //    @Ignore
    @Test
    public void testDelete() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Organ entity = new Organ();
        entity.setId("100");
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/purview/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson.getBytes()))
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    //    @Ignore
    @Test
    public void testAdd() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Purview entity = new Purview();
        entity.setId(11);
        entity.setName("管理页面");
        entity.setType("menu");
        entity.setUrl("/projindex_admin.html");
        entity.setValid(1);
        entity.setSortNum(1);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/purview/add")
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
        Purview entity = new Purview();
        entity.setId(2);
        entity.setName("管理页面111");
        entity.setType("menu");
        entity.setUrl("/projindex_admin.html");
        entity.setValid(1);
        entity.setSortNum(1);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/purview/save")
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
                "/sys/purview/find")
                .param("id", "1")) //管理页面
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
