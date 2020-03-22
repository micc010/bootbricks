package com.github.rogerli.system.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.framework.entity.EntityInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author roger.li
 * @date 2015/8/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_login")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS系统用户")
public class Login implements EntityInterface<String> {

    public static final String DEFAULT_PASSWORD = "11111";

    @Id
    private String id;
    @Column
    private Boolean available;
    @Column
    private LocalDateTime createTime;
    @Column
    private String creator;
    @Column
    private LocalDateTime modifyTime;
    @Column
    private String modifier;
    @Column
    @Length(min = 4, max = 30)
    @ApiModelProperty("用户名")
    private String username;
    @Column
    @Length(min = 8, max = 20)
    @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/")
    @ApiModelProperty("密码")
    private String password;
    @Column
    @NotBlank
    @ApiModelProperty("真实姓名")
    private String fullName;
    @ApiModelProperty("头像")
    private String icon;
    @Column
    @Pattern(regexp = "^[0-1]$")
    @ApiModelProperty("是否被锁定")
    private Integer locked;
    @Column
    @NotBlank
    @ApiModelProperty("单位id")
    private String organId;
    @Column
    @NotBlank
    @ApiModelProperty("单位名称")
    private String organName;

}