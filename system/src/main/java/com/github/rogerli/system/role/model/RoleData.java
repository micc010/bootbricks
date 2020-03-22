/**
 * @文件名称： RolePurview.java
 * @文件描述：
 * @版权所有：(C)2016-2028
 * @公司：
 * @完成日期: 2016/12/5
 * @作者 ： Roger
 */
package com.github.rogerli.system.role.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.purview.entity.Purview;
import com.github.rogerli.system.role.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Roger
 * @description
 * @create 2016/12/5 11:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS角色数据对象")
public class RoleData extends Role {

    @ApiModelProperty("权限清单")
    private List<Purview> purviewList;

}
