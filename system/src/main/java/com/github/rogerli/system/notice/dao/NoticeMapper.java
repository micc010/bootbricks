package com.github.rogerli.system.notice.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.notice.entity.Notice;
import com.github.rogerli.system.notice.entity.NoticeUser;
import com.github.rogerli.system.notice.model.NoticeRowData;
import com.github.rogerli.system.notice.model.NoticeQuery;
import com.github.rogerli.system.record.annotation.RecordTransient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户mapper
 *
 * @author roger.li
 * @since 2019/5/20
 */
@Mapper
public interface NoticeMapper extends DaoMapper<Notice> {

    /**
     * 插入消息与用户多对多关联表
     *
     * @param user
     *
     * @return
     */
    @RecordTransient
    int insertNoticeUser(NoticeUser user);

    /**
     * 删除消息与用户多对多关联表
     *
     * @param user
     *
     * @return
     */
    @RecordTransient
    int deleteNoticeUser(NoticeUser user);

    /**
     * 根据消息ID删除关联
     *
     * @param id
     *
     * @return
     */
    @RecordTransient
    int deleteNoticeUserByKey(String id);

    /**
     * 根据用户等条件查询消息
     *
     * @param query
     * @return
     */
    List<NoticeRowData> findByQuery(NoticeQuery query);

}