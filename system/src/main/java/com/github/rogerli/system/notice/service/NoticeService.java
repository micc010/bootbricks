package com.github.rogerli.system.notice.service;

import com.github.rogerli.system.notice.dao.NoticeMapper;
import com.github.rogerli.system.notice.entity.Notice;
import com.github.rogerli.system.notice.model.NoticeRowData;
import com.github.rogerli.system.notice.model.NoticeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService extends AbstractService<NoticeMapper, Notice, Long> {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    protected NoticeMapper getMapper() {
        return noticeMapper;
    }

    // TODO: 2019/7/4 向单位发送

    // TODO: 2019/7/4 向单位取消

    // TODO: 2019/7/4 向角色发送

    // TODO: 2019/7/4 向角色取消

    // TODO: 2019/7/4 向系统发送

    // TODO: 2019/7/4 向系统取消

    // TODO: 2019/7/4 按自定义发送

    // TODO: 2019/7/4 按自定义取消

    /**
     * 根据用户等条件查询消息
     *
     * @param query
     * @return
     */
    public List<NoticeRowData> findByQuery(NoticeQuery query) {
        return noticeMapper.findByQuery(query);
    }

}
