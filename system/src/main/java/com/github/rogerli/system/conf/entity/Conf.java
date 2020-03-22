package com.github.rogerli.system.conf.entity;

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

import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *
 * @author roger.li
 * @since 2019/5/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_conf")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统配置")
public class Conf implements Entity<Integer>{

    @Id
    @KeySql(genId = UUIDGenerator.class)
    private Integer id;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("参数值")
    private String value;
    @ApiModelProperty("是否有效")
    private Integer valid;

}
