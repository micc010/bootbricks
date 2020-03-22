package com.github.rogerli.system.file.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.utils.FileNameUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * @author roger.li
 * @since 2019/6/20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("FILE保存参数对象")
public class FileItem implements Serializable {

    @ApiModelProperty("主键id")
    private String itemId;
    @ApiModelProperty("主键类")
    private String itemType;
    @ApiModelProperty("文件id列表")
    private List<String> idList;

}
