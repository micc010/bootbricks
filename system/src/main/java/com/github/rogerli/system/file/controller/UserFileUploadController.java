
package com.github.rogerli.system.file.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.file.model.FileData;
import com.github.rogerli.system.file.service.FileService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author roger.li
 * @since 2019/6/4
 */
@Slf4j
@RestController
@RequestMapping(UserFileUploadController.CONTENT_PATH)
@Api(tags = "USER上传文件")
public class UserFileUploadController extends AbstractRestController<com.github.rogerli.system.file.entity.File, String> {

    public static final String CONTENT_PATH = "user/file";

    @Autowired
    private FileService fileService;
//    @Autowired
//    private HttpServletRequest request;

    @Override
    protected ServiceInterface getService() {
        return fileService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping(
            name = "上传文件",
            value = "upload",
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("上传文件")
    public Result upload(@ApiParam("文件列表") @RequestParam List<MultipartFile> file) throws IOException {
        log.debug("rest path:" + getContentPath() + "/upload:", file);

        FileData fileData = fileService.uploadFile(file);

        return new Result().ok(MessageUtils.get("upload.ok"), null, fileData);
    }

}
