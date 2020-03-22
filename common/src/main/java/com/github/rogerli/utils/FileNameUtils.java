package com.github.rogerli.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Date;

/**
 * @author roger.li
 * @since 2019/6/20
 */
@Component
public class FileNameUtils {

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    /**
     * 生成物理路径
     *
     * @return
     */
    public String generateFullPhysicalPath(String relativePath, String saveName) {
        return new StringBuffer()
                .append(filePath)
                .append(relativePath)
                .append(saveName).toString();
    }

    /**
     * 物理路径
     *
     * @return
     */
    public String getFolderPath() {
        return new StringBuffer()
                .append(filePath)
                .append(getRelativePath()).toString();
    }

    /**
     * 相对路径
     *
     * @return
     */
    public String getRelativePath() {
        return new StringBuffer()
                .append(File.separator)
                .append(DateUtils.format(new Date(), "yyyy-MM-dd"))
                .append(File.separator).toString();
    }

    /**
     * 文件根目录
     *
     * @return
     */
    public String getRoot() {
        return filePath;
    }

    /**
     * 根据文件完整名获得文件名
     *
     * @param fileName
     *
     * @return
     */
    public String getFileName(String fileName) {
        Assert.hasText(fileName, MessageUtils.get("file.not.exists"));
        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
        } else {
            return fileName;
        }
    }

    /**
     * 根据文件名获得后缀
     *
     * @param fileName
     *
     * @return
     */
    public String getSuffix(String fileName) {
        Assert.hasText(fileName, MessageUtils.get("file.not.exists"));
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        } else {
            return "";
        }
    }

}
