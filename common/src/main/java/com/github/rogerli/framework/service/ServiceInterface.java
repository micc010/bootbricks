package com.github.rogerli.framework.service;

import com.github.rogerli.framework.dao.DaoInterface;
import com.github.rogerli.framework.entity.EntityInterface;
import com.github.rogerli.framework.model.Query;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ServiceInterface 服务接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface ServiceInterface<D extends DaoInterface<E, Q, K>,
        E extends EntityInterface<K>, Q extends Query<K>, K> {

    D getDao();

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    default Pair<Integer, String> insert(E entity) {

        return getDao().insert(entity);
    }

    /**
     * 批量新增
     *
     * @param entityList
     * @return
     */
    @Transactional
    default Pair<Integer, List<String>> batchInsert(List<E> entityList) {
        return getDao().batchInsert(entityList);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    default Pair<Integer, String> update(E entity) {
        return getDao().update(entity);
    }

    /**
     * 批量修改
     *
     * @param entityList
     * @return
     */
    @Transactional
    default Pair<Integer, List<String>> batchUpdate(List<E> entityList) {
        return getDao().batchUpdate(entityList);
    }

    /**
     * 根据条件删除
     *
     * @param entity
     * @return
     */
    default Pair<Integer, List<String>> delete(E entity) {
        return getDao().delete(entity);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    default Pair<Integer, String> deleteById(K id) {
        return getDao().deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param entityList
     * @return
     */
    @Transactional
    default Pair<Integer, List<String>> batchDelete(List<E> entityList) {
        return batchDelete(entityList);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    default E findById(K id) {
        return getDao().findById(id);
    }

    /**
     * 根据条件查询
     *
     * @param entity
     * @return
     */
    default E findOne(E entity) {
        return getDao().findOne(entity);
    }

    /**
     * 根据简单条件查询
     *
     * @param entity
     * @return
     */
    default List<E> findList(E entity) {
        return getDao().findList(entity);
    }

    /**
     * 根据复杂条件查询
     *
     * @param query
     * @return
     */
    default List<E> findListByQuery(Q query) {
        return getDao().findListByQuery(query);
    }

}
