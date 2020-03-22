package com.github.rogerli.system.record.entity;

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
import java.util.Date;

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
@Table(name = "sys_record")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS修改记录")
public class Record implements Entity<Long>{

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @ApiModelProperty("用户id")
    private String loginId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("修改时间")
    private Date modifyTime;
    @ApiModelProperty("修改类型")
    private Integer modifyType;
    @ApiModelProperty("用户IP")
    private String logIp;
    @ApiModelProperty("记录id")
    private String classId;
    @ApiModelProperty("记录对象名")
    private String className;
    @ApiModelProperty("调整前、后的json对象")
    private String content;

}
