package com.github.rogerli.framework.dao;

import com.github.rogerli.framework.entity.EntityInterface;
import com.github.rogerli.framework.model.Query;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * DaoInterface Dao接口
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface DaoInterface<E extends EntityInterface<K>, Q extends Query<K>, K> {

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    Pair<Integer, String> insert(E entity);

    /**
     * 批量新增
     *
     * @param entityList
     * @return
     */
    Pair<Integer, List<String>> batchInsert(List<E> entityList);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    Pair<Integer, String> update(E entity);

    /**
     * 批量修改
     *
     * @param entityList
     * @return
     */
    Pair<Integer, List<String>> batchUpdate(List<E> entityList);

    /**
     * 根据条件删除
     *
     * @param entity
     * @return
     */
    Pair<Integer, List<String>> delete(E entity);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    Pair<Integer, String> deleteById(K id);

    /**
     * 批量删除
     *
     * @param entityList
     * @return
     */
    Pair<Integer, List<String>> batchDelete(List<E> entityList);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    E findById(K id);

    /**
     * 根据条件查询
     *
     * @param entity
     * @return
     */
    E findOne(E entity);

    /**
     * 根据简单条件查询
     *
     * @param entity
     * @return
     */
    List<E> findList(E entity);

    /**
     * 根据复杂条件查询
     *
     * @param query
     * @return
     */
    List<E> findListByQuery(Q query);

}
