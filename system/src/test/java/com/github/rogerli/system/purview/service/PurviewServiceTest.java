/**
 * @文件名称： LoginMapperTest.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/11/30
 * @作者 ： Roger
 */
package com.github.rogerli.system.purview.service;

import com.github.rogerli.TestSystemApplication;
import com.github.rogerli.system.login.entity.Login;
import com.github.rogerli.system.purview.entity.Purview;
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
public class PurviewServiceTest {

    @Autowired
    private PurviewService purviewService;


    //    @Ignore
    @Test
    public void testsInsertUser() {
        Purview entity = new Purview();
        entity.setValid(1);
        entity.setName("主页");
        entity.setSortNum(2);
        entity.setType("Menu");
        entity.setUrl("/index");
        int i = purviewService.insertSelective(entity);
        Assert.isTrue(i == 1, "error");
    }

}