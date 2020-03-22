package com.github.rogerli.system.notice.entity;

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

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_notice")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统消息")
public class Notice implements Entity<Long> {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("消息类型，sys系统消息全体看，custom自定义消息指定用户看，org特定单位看，role特定角色看")
    private String msgType;
    @ApiModelProperty("消息")
    private String msg;

}
