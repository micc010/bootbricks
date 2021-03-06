package com.github.rogerli.system.file.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.file.entity.File;
import com.github.rogerli.utils.FileNameUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.io.FilenameUtils;

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
@ApiModel("FILE保存返回参数对象")
public class FileData implements Serializable {

    @ApiModelProperty("成功上传的文件")
    private List<File> successList;

}
