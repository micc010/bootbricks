package com.github.rogerli.system.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.common.mapper.UUIDGenerator;
import com.github.rogerli.framework.entity.Entity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "sys_file")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("SYS文件信息")
public class File implements Entity<String> {

    @Id
    @KeySql(genId = UUIDGenerator.class)
    private String id;
    private String itemId;
    private String itemType;
    private String saveName;
    private String originName;
    private Long fileSize;
    private String filePath;
    private String fileUrl;
    private String shortUrl;
    private String md5;
    private Date createTime;
    private String username;

}