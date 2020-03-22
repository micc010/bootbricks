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
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_notice_user")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统消息对象")
public class NoticeUser implements Serializable {

    @ApiModelProperty("消息id")
    private Date noticeId;
    @ApiModelProperty("消息类型，sys系统消息全体看，custom自定义消息指定用户看，org特定单位看，role特定角色看")
    private String msgType;
    @ApiModelProperty("用户id")
    private String loginId;
    @ApiModelProperty("单位id")
    private String organId;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("是否已读")
    private Boolean isRead;

}
