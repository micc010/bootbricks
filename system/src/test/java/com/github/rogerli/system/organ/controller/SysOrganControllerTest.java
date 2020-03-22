/**
 * @文件名称： RoleController.java
 * @文件描述：
 * @版权所有：(C)2017-2028
 * @公司：
 * @完成日期: 2017/1/24
 * @作者 ： Roger
 */
package com.github.rogerli.system.organ.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.organ.entity.Organ;
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
public class SysOrganControllerTest {

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
        entity.setId("9e515813-4a35-4240-9364-6a280ecdb712"); // 上林县公安消防大队
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/organ/delete")
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
        Organ entity = new Organ();
        entity.setName("单位测试");
        entity.setCode("11111");
        entity.setShortName("测试");
        entity.setParentId("test_1");
        entity.setValid(1);
        entity.setCheckable(1);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/organ/add")
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
        Organ entity = new Organ();
        entity.setId("9e7f60e9-2dbb-4d58-98f3-a091daf73467");
        entity.setName("南宁市凤岭儿童公园111");
        entity.setCode("00030192");
        entity.setShortName("市凤岭儿童公园");
        entity.setParentId("25222eb5-dec2-4fc5-95bb-dbbcce38dada");
        entity.setValid(1);
        entity.setCheckable(1);
        String requestJson = objectMapper.writeValueAsString(entity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST,
                "/sys/organ/save")
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
                "/sys/organ/find")
                .param("id", "1")) //
                .andExpect(MockMvcResultMatchers.content().json("{'status':0}", false))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
