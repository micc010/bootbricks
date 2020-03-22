package com.github.rogerli.system.record.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS修改项记录")
public class RecordItem implements Serializable {

    @ApiModelProperty("项名称")
    private String name;
    @ApiModelProperty("旧值")
    private Object oldVal;
    @ApiModelProperty("新值")
    private Object newVal;

}
