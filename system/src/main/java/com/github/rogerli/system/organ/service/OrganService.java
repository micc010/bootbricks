/**
 * @文件名称： LoginService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/1
 * @作者 ： Roger
 */
package com.github.rogerli.system.organ.service;

import com.github.pagehelper.PageHelper;
import com.github.rogerli.system.organ.dao.OrganMapper;
import com.github.rogerli.system.organ.entity.Organ;
import com.github.rogerli.system.organ.model.OrganData;
import com.github.rogerli.system.organ.model.OrganQuery;
import com.github.rogerli.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roger
 * @description
 * @create 2016/12/1 0:47
 */
@Slf4j
@Service
public class OrganService extends AbstractService<OrganMapper, Organ, String> {

    @Autowired
    private OrganMapper organMapper;

    @Override
    public OrganMapper getMapper() {
        return organMapper;
    }

    /**
     * 级联查询单位
     *
     * @param query
     * @return
     */
    public List<OrganData> findCascade(OrganQuery query) {
        log.debug("Find Organ Cascade:", query.toString());
        PageHelper.orderBy("sort_num asc");
        List<Organ> list = organMapper.selectAll();

        List<OrganData> rows = list.stream().map(topic -> new OrganData().initFrom(topic))
                .collect(Collectors.toList());

        List<OrganData> organs = TreeUtils.listToTreeRoot(rows, "id", "parentId", "subOrgans");

        if (StringUtils.hasText(query.getId())) {
            organs = organs.stream().filter(organ -> query.getId().equals(organ.getId())).collect(Collectors.toList());
        }

        return organs;
    }
}
