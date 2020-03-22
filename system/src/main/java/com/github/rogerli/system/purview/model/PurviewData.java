package com.github.rogerli.system.purview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.rogerli.system.purview.entity.Purview;
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
public class PurviewData extends Purview {

    private List<PurviewData> subPurviews;

    public PurviewData initFrom(Purview purview) {
        BeanUtils.copyProperties(purview, this, new String[]{"subPurviews"});
        return this;
    }

}
