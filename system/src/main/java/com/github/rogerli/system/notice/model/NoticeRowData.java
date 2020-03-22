package com.github.rogerli.system.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.notice.entity.Notice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统消息单条数据")
public class NoticeRowData extends Notice {

    @ApiModelProperty("用户id")
    private String loginId;
    @ApiModelProperty("单位id")
    private String organId;
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("是否已读")
    private Boolean isRead;

}
