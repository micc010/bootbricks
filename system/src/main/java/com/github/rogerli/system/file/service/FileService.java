package com.github.rogerli.system.file.service;

import com.github.rogerli.system.file.dao.FileMapper;
import com.github.rogerli.system.file.entity.File;
import com.github.rogerli.system.file.model.FileData;
import com.github.rogerli.system.file.model.FileItem;
import com.github.rogerli.system.file.model.FileItemQuery;
import com.github.rogerli.system.file.model.FileQuery;
import com.github.rogerli.utils.DateUtils;
import com.github.rogerli.utils.EncryptUtils;
import com.github.rogerli.utils.FileNameUtils;
import com.github.rogerli.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService extends AbstractService<FileMapper, File, String> {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileNameUtils pathUtils;
    @Autowired
    private EncryptUtils encryptUtils;

    @Override
    protected FileMapper getMapper() {
        return fileMapper;
    }

    /**
     * 根据主键id更新文件
     *
     * @param model
     */
    public void updateByItemId(FileItem model) {
        getMapper().updateByItemId(model);
    }

    /**
     * 根据主键id删除文件
     *
     * @param model
     */
    public void deleteByItemId(FileItem model) {
        getMapper().deleteByItemId(model);
    }

    /**
     * 根据条件查询文件
     *
     * @param idList
     *
     * @return
     */
    public List<File> findByIdList(List<String> idList) {
        if (idList == null || idList.size() == 0) {
            return new ArrayList<File>();
        }
        FileQuery query = new FileQuery();
        query.setIdList(idList);
        return getMapper().findByIdList(query);
    }

    /**
     * 根据条件查询文件
     *
     * @param idList
     *
     * @return
     */
    public List<File> findByMD5List(List<String> idList) {
        if (idList == null || idList.size() == 0) {
            return new ArrayList<File>();
        }
        FileQuery query = new FileQuery();
        query.setIdList(idList);
        return getMapper().findByMD5List(query);
    }

    /**
     * 根据MD5查询文件
     *
     * @param md5
     *
     * @return
     */
    public File fingByMD5(String md5) {
        Assert.hasText(md5, MessageUtils.get("entity.notNull"));
        return fileMapper.selectOne(new File().setMd5(md5));
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     *
     * @return 文件返回对象
     *
     * @throws IOException
     */
    public FileData uploadFile(List<MultipartFile> file) throws IOException {
        FileData fileData = new FileData().setSuccessList(new ArrayList<>());
        Date date = new Date();
        for (MultipartFile multiFile : file) {
            if (!StringUtils.hasText(multiFile.getOriginalFilename())) {
                continue;
            }
            String md5 = encryptUtils.encryptMD5ToString(multiFile.getBytes());
            File md5File = fingByMD5(md5);
            if (md5File == null) {
                String originName = new String(multiFile.getOriginalFilename().getBytes(), "GBK");
                String suffix = pathUtils.getSuffix(originName);
                String saveName = pathUtils.getFileName(originName) + DateUtils.format(date, "yyyyMMddHHmmssSSS") + suffix;
                String filePath = pathUtils.getRelativePath();
                String fullPhysicalPath = pathUtils.generateFullPhysicalPath(filePath, saveName);

                java.io.File dir = new java.io.File(pathUtils.getFolderPath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                java.io.File f = new java.io.File(fullPhysicalPath);
                multiFile.transferTo(f);

                File fi = new File()
                        .setCreateTime(new Date())
                        .setFilePath(filePath)
                        .setFileSize(multiFile.getSize())
                        .setUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                        .setMd5(md5)
                        .setOriginName(originName)
                        .setSaveName(saveName);
                insertSelective(fi);

                fileData.getSuccessList().add(fi);
            } else {
                fileData.getSuccessList().add(md5File);
            }
        }
        return fileData;
    }

    /**
     * @param query
     *
     * @return
     */
    public List<File> findByItemList(FileItemQuery query) {
        return fileMapper.findByItemList(query);
    }
}
