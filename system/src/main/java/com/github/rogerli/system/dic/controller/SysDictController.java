package com.github.rogerli.system.dic.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.dic.entity.Dict;
import com.github.rogerli.system.dic.model.DictQuery;
import com.github.rogerli.system.dic.service.DictService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(SysDictController.CONTENT_PATH)
@Api(tags = "SYS系统字典管理")
public class SysDictController extends AbstractRestController<Dict, String> {

    public static final String CONTENT_PATH = "sys/dict";

    @Autowired
    private DictService dictService;

    @Override
    protected ServiceInterface getService() {
        return dictService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 删除字典
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(
            name = "删除字典",
            value = {"/delete"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除字典")
    public Result delete(@ApiParam("删除字典参数") @RequestBody Dict entity) {
        return super.delete(entity);
    }

    /**
     * 添加字典
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加字典",
            value = {"/add"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("添加字典")
    public Result add(@ApiParam("添加字典参数") @RequestBody Dict entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.add(entity, bindingResult);
    }

    /**
     * 保存字典
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存字典",
            value = {"/save"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("保存字典")
    public Result save(@ApiParam("保存字典参数") @RequestBody Dict entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(entity, bindingResult);
    }

    /**
     * 查询单个字典项
     *
     * @param id
     *
     * @return
     */
    @RequestMapping(
            name = "查询单个字典",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("查询单个字典")
    public Result find(@ApiParam("字典id") @RequestParam String id) {
        return super.find(id);
    }

    /**
     * 查询单个字典
     *
     * @param type
     *
     * @return
     */
    @RequestMapping(
            name = "查询单个字典",
            value = {"/by-type"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("查询单个字典")
    public Result findByType(@ApiParam("字典类型") @RequestParam String type) {
        log.debug("rest path:" + getContentPath() + "/by-type:", type);
        List<Dict> dictList = dictService.findByType(type);
        return new Result().ok(MessageUtils.get("find.ok"), null, dictList);
    }

    /**
     * 查询分页
     *
     * @param query
     *
     * @return
     */
    @RequestMapping(
            name = "查询分页",
            value = {"/page"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询分页")
    public Result page(@ApiParam("查询参数") @RequestBody DictQuery query) {
        return super.page(query);
    }

    /**
     * 查询不分页
     *
     * @param query
     *
     * @return
     */
    @RequestMapping(
            name = "查询不分页",
            value = {"/select"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("查询不分页")
    public Result select(@ApiParam("查询参数") @RequestBody DictQuery query) {
        return super.select(query);
    }


    /**
     * @param entity
     *
     * @return
     */
    @RequestMapping(
            name = "设置有效",
            value = {"/valid"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("设置有效")
    public Result valid(@ApiParam("字典参数") @RequestBody Dict entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(new Dict().setId(entity.getId()).setValid(1), bindingResult);
    }


    /**
     * @param entity
     *
     * @return
     */
    @RequestMapping(
            name = "设置无效",
            value = {"/invalid"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("设置无效")
    public Result invalid(@ApiParam("字典参数") @RequestBody Dict entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(new Dict().setId(entity.getId()).setValid(0), bindingResult);
    }

}
