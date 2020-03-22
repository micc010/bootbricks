package com.github.rogerli.system.file.dao;

import com.github.rogerli.framework.dao.DaoMapper;
import com.github.rogerli.system.file.entity.File;
import com.github.rogerli.system.file.model.FileItem;
import com.github.rogerli.system.file.model.FileItemQuery;
import com.github.rogerli.system.file.model.FileQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper extends DaoMapper<File> {

    /**
     * 根据主键id更新文件
     *
     * @param model
     */
    void updateByItemId(FileItem model);

    /**
     * 根据主键id删除文件
     *
     * @param model
     */
    void deleteByItemId(FileItem model);

    /**
     * 根据idList删除
     *
     * @param model
     */
    void deleteByIdList(FileItem model);

    /**
     * 根据条件查询文件
     *
     * @param query
     * @return
     */
    List<File> findByIdList(FileQuery query);

    /**
     * 根据MD5条件查询文件
     *
     * @param query
     * @return
     */
    List<File> findByMD5List(FileQuery query);

    /**
     * 根据item查询文件
     *
     * @param query
     * @return
     */
    List<File> findByItemList(FileItemQuery query);

}
