package com.github.rogerli.system.organ.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.common.mapper.UUIDGenerator;
import com.github.rogerli.framework.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 单位
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_organ")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS单位")
public class Organ implements Entity<String>{

    @Id
    @KeySql(genId = UUIDGenerator.class)
    private String id;
    @ApiModelProperty("单位全称")
    private String name;
    @ApiModelProperty("单位编码")
    private String code;
    @ApiModelProperty("单位简称")
    private String shortName;
    @ApiModelProperty("单位类型")
    private String type;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("序号")
    private Integer sortNum;
    @ApiModelProperty("是否可选")
    private Integer checkable;
    @ApiModelProperty("父单位id")
    private String parentId;
    @ApiModelProperty("父单位名称")
    private String parentName;

}