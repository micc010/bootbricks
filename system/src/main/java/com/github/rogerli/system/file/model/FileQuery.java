package com.github.rogerli.system.file.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/7/10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS文件信息参数对象")
public class FileQuery extends Query<String> {

    private String itemId;
    private String itemType;
    private String saveName;
    private String originName;
    private Long fileSize;
    private String filePath;
    private String fileUrl;
    private String shortUrl;
    private String md5;
    private Date createTime;
    private String username;
    private List<String> idList;

}
