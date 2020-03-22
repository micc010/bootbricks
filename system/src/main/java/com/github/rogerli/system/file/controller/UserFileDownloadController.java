
package com.github.rogerli.system.file.controller;

import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.framework.exception.RestException;
import com.github.rogerli.system.file.model.FileItem;
import com.github.rogerli.system.file.service.FileService;
import com.github.rogerli.utils.FileNameUtils;
import com.github.rogerli.utils.MessageUtils;
import com.github.rogerli.utils.UUIDUtils;
import com.github.rogerli.utils.ZipUtils;
import com.google.common.io.BaseEncoding;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author roger.li
 * @since 2019/6/4
 */
@Slf4j
@RestController
@RequestMapping(UserFileDownloadController.CONTENT_PATH)
@Api(tags = "USER下载文件")
public class UserFileDownloadController extends AbstractRestController<com.github.rogerli.system.file.entity.File, String> {

    public static final String CONTENT_PATH = "user/file";

    @Autowired
    private FileService fileService;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FileNameUtils pathUtils;

    /**
     * @return service
     */
    @Override
    protected ServiceInterface getService() {
        return fileService;
    }

    /**
     * @return contextPath
     */
    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 下载附件
     *
     * @return result
     */
    @RequestMapping(
            name = "下载附件",
            value = "/download",
            produces = {"application/json"},
            method = RequestMethod.GET
    )
    @ApiOperation("下载附件")
    public void download(@ApiParam("文件id") @RequestParam String fileId) throws IOException {
        log.debug("rest path:" + getContentPath() + "/download:" + fileId);
        com.github.rogerli.system.file.entity.File file = fileService.findByKey(fileId);
        Assert.notNull(file, MessageUtils.get("file.not.exists"));

        File f = new File(pathUtils.getRoot() + file.getFilePath() + file.getSaveName());
        Assert.isTrue(f.exists(), MessageUtils.get("file.not.exists"));

        String filename = getFilename(file.getOriginName());

        String contentType = request.getServletContext().getMimeType(file.getOriginName());
        contentType = getContentType(contentType);

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename="
                + filename);
        response.setHeader("Content-Length", Long.toString(file.getFileSize()));
        response.setContentType(contentType);

        download(f);
    }

    /**
     * 打包下载
     *
     * @param item
     *
     * @throws IOException
     */
    @RequestMapping(
            name = "打包下载",
            value = "/package",
            method = RequestMethod.POST
    )
    @ApiOperation("打包下载")
    public void packageDownload(@ApiParam("文件列表") @RequestBody FileItem item) throws IOException {
        log.debug("rest path:" + getContentPath() + "/package");
        Assert.notEmpty(item.getIdList(), MessageUtils.get("list.notEmpty"));
        // 文件记录
        List<com.github.rogerli.system.file.entity.File> fileList = fileService.findByIdList(item.getIdList());
        Assert.notEmpty(fileList, MessageUtils.get("list.notEmpty"));
        // 服务器文件
        List<File> files = new ArrayList<>();
        fileList.stream().forEach(file -> {
            File f = new File(pathUtils.getRoot() + file.getFilePath() + file.getSaveName());
            Assert.isTrue(f.exists(), MessageUtils.get("file.not.exists"));
            files.add(f);
        });

        com.github.rogerli.system.file.entity.File firstFile = fileList.stream().findFirst().get();

        String name = pathUtils.getFileName(firstFile.getSaveName()) + ".zip";
        String zipName = pathUtils.getRoot() + firstFile.getFilePath() + pathUtils.getFileName(UUIDUtils.id()) + ".zip";
        File zipFile = new File(zipName);
        // 打包压缩
        ZipUtils.zipFiles(files, zipFile);

        Long fileSize = fileList.stream().mapToLong(file -> file.getFileSize()).sum();

        String filename = getFilename(name);

        String contentType = request.getServletContext().getMimeType(name);

//        response.setHeader("Accept-Ranges", "bytes");
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setHeader("Content-Length", Long.toString(fileSize));
        response.setContentType(contentType);

        download(zipFile);
    }

    /**
     * 如果是TEXT_PLAIN 直接替换为流下载
     *
     * @param contentType
     *
     * @return
     */
    private String getContentType(String contentType) {
        if (MediaType.TEXT_PLAIN.toString().equals(contentType)) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=gbk";
        }
        return contentType;
    }

    /**
     * 根据请求头获取文件名称
     *
     * @param name
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    private String getFilename(String name) throws UnsupportedEncodingException {
        String filename = name;
        String agent = request.getHeader("User-Agent");
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = "=?utf-8?B?" + BaseEncoding.base64().encode(filename.getBytes("UTF-8")) + "?=";
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "UTF-8");
        }
        return filename;
    }

    /**
     * 下载
     *
     * @param file
     *
     * @return
     */
    private void download(File file) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int i;
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (IOException e) {
            log.error("file download failed:", e);
            throw new RestException(MessageUtils.get("file.download.failed"));
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("buffered input stream close failed:", e);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("file input stream close failed:", e);
                }
            }
        }
    }

}
