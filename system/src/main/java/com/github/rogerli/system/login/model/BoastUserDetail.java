package com.github.rogerli.system.login.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

/**
 * @author roger.li
 * @since 2019/5/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS用户授权管理对象")
public class BoastUserDetail implements UserDetails {

    private String id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("真实姓名")
    private String fullname;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("单位id")
    private String organId;
    @ApiModelProperty("单位名称")
    private String organName;
    @ApiModelProperty("密码最后修改时间")
    private Date lastPasswordResetDate;
    @ApiModelProperty("权限清单")
    private Set<GrantedAuthority> authorities;
    @ApiModelProperty("用户未过期")
    private boolean accountNonExpired = true;
    @ApiModelProperty("用户未锁定")
    private boolean accountNonLocked = true;
    @ApiModelProperty("登陆授权未过期")
    private boolean credentialsNonExpired = true;
    @ApiModelProperty("是否有效")
    private boolean enabled = true;

    public BoastUserDetail(String id, String username, String fullname, String password, String organId,
                           String organName, Date lastPasswordResetDate, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.organId = organId;
        this.organName = organName;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoastUserDetail) {
            return ((BoastUserDetail) obj).getUsername().equals(getUsername());
        }
        return false;
    }
}
