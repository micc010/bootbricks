package com.github.rogerli.framework.web;

import com.github.rogerli.framework.entity.EntityInterface;
import com.github.rogerli.framework.model.Query;
import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * QueryEndpointInterface
 *
 * @author roger.li
 * @date 2015/8/24
 */
public interface QueryEndpointInterface<S extends ServiceInterface,
        E extends EntityInterface<K>, Q extends Query<K>, K> extends MvcEndpointInterface<S> {

    @GetMapping(
            name = "根据id查询",
            value = {"/query/{id}"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("根据id查询")
    default Result find(@PathVariable K id) {
        logger().info("Find By Id: {}", id.toString());
        EntityInterface entity = getService().findById(id);
        logger().debug("Find Entity Data: {}", entity.toString());
        return new Result().ok(MessageUtils.get("find.ok"), entity, null);
    }

    @PostMapping(
            name = "根据复杂条件查询",
            value = {"/query"},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("根据简单条件查询")
    default Result findList(@RequestBody Q query) {
        logger().info("Find By Query: {}", query.toString());
        List<E> list = getService().findListByQuery(query);
        logger().debug("Find Entity Data List: {}", list.toString());
        return new Result().ok(MessageUtils.get("find.ok"), list, null);
    }

}
