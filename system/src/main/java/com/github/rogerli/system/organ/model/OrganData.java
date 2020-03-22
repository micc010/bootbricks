package com.github.rogerli.system.organ.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.organ.entity.Organ;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;


/**
 * @author roger.li
 * @since 2019/6/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("USER单位")
public class OrganData extends Organ {

    private List<OrganData> subOrgans;

    public OrganData initFrom(Organ topic) {
        BeanUtils.copyProperties(topic, this, new String[]{"subOrgans"});
        return this;
    }

}
