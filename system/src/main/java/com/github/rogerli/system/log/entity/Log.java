package com.github.rogerli.system.log.entity;

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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_log")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统日志")
public class Log implements Entity<Long> {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @ApiModelProperty("用户账号")
    private String username;
    @ApiModelProperty("姓名")
    private String fullName;
    @ApiModelProperty("记录时间")
    private Date logTime;
    @ApiModelProperty("请求IP")
    private String logIp;
    @ApiModelProperty("请求名称")
    private String optName;
    @ApiModelProperty("请求地址")
    private String logUrl;

}