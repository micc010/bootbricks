package com.github.rogerli.framework.web;

import com.github.rogerli.framework.entity.EntityInterface;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * CRUDEndpointInterface
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface CRUDEndpointInterface<S extends ServiceInterface,
        E extends EntityInterface<K>, K> extends MvcEndpointInterface<S> {

    @PostMapping(
            name = "新增",
            value = {"/add"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("新增")
    default Result add(@RequestBody E entity) {
        logger().info("Add Entity: {}", entity.toString());
        Pair<Integer, String> result = getService().insert(entity);
        logger().debug("Add Entity Success,Entity Id Is: {}", result.getRight());
        return new Result().ok(MessageUtils.get("find.ok"));
    }

    @PostMapping(
            name = "修改",
            value = {"/update"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("修改")
    default Result update(@RequestBody E entity) {
        logger().info("Update Entity: {}", entity.toString());
        Pair<Integer, String> result = getService().update(entity);
        logger().debug("Update Entity Success,Entity Id Is: {}", result.getRight());
        return new Result().ok(MessageUtils.get("find.ok"));
    }

    @PostMapping(
            name = "删除",
            value = {"/update"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("删除")
    default Result delete(@RequestBody E entity) {
        logger().info("Delete Entity: {}", entity.toString());
        Pair<Integer, String> result = getService().delete(entity);
        logger().debug("Delete Entity Success,Entity Id Is: {}", result.getRight());
        return new Result().ok(MessageUtils.get("find.ok"));
    }


}
