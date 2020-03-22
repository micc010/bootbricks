/**
 * @文件名称： LoginService.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/1
 * @作者 ： Roger
 */
package com.github.rogerli.system.purview.service;

import com.github.rogerli.system.purview.dao.PurviewMapper;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.purview.model.PurviewData;
import com.github.rogerli.system.purview.model.PurviewQuery;
import com.github.rogerli.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roger
 * @description
 * @create 2016/12/1 0:47
 */
@Slf4j
@Service
public class PurviewService extends AbstractService<PurviewMapper, Purview, Integer> {

    @Autowired
    private PurviewMapper purviewMapper;

    @Override
    public PurviewMapper getMapper() {
        return purviewMapper;
    }

    /**
     * @param query
     *
     * @return
     */
    public List<PurviewData> findCascade(PurviewQuery query) {
        log.debug("Find Purview Cascade:", query.toString());
        List<Purview> list = purviewMapper.selectAll();

        List<PurviewData> rows = list.stream().map(purview -> new PurviewData().initFrom(purview))
                .collect(Collectors.toList());

        List<PurviewData> purviews = TreeUtils.listToTreeRoot(rows, "id", "parentId", "subPurviews");

        if (query.getId() != null && query.getId() != 0) {
            purviews = purviews.stream().filter(organ -> query.getId().equals(organ.getId())).collect(Collectors.toList());
        }

        return purviews;
    }
}
