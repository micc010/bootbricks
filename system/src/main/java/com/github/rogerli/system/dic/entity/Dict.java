package com.github.rogerli.system.dic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.framework.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_dict")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统字典")
public class Dict implements Entity<Integer> {

    // 层级字典未考虑

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("代码")
    private String code;
    @ApiModelProperty("是否有效")
    private Integer valid;

}
