package com.github.rogerli.system.notice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.notice.entity.Notice;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统消息数据集合")
public class NoticeData extends Notice {

}
