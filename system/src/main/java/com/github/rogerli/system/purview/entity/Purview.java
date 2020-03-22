package com.github.rogerli.system.purview.entity;

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
import tk.mybatis.mapper.code.IdentityDialect;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_purview")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统权限")
public class Purview implements Entity<Integer>{

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("权限类型")
    private String type;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("权限地址")
    private String url;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("序号")
    private Integer sortNum;
    @ApiModelProperty("父权限id")
    private Integer parentId;

}