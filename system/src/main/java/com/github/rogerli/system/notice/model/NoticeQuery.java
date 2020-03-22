package com.github.rogerli.system.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统消息参数对象")
public class NoticeQuery extends Query<Long> {

    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("消息类型，sys系统消息全体看，custom自定义消息指定用户看，org特定单位看，role特定角色看")
    private String msgType;
    @ApiModelProperty("消息")
    private String msg;
    @ApiModelProperty("用户id")
    private String loginId;
    @ApiModelProperty("是否已读")
    private Boolean isRead;

}
