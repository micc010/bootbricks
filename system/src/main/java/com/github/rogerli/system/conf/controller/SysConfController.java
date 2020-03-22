package com.github.rogerli.system.conf.controller;

import com.github.rogerli.framework.model.Result;
import com.github.rogerli.framework.service.ServiceInterface;
import com.github.rogerli.system.conf.entity.Conf;
import com.github.rogerli.system.conf.model.ConfQuery;
import com.github.rogerli.system.conf.service.ConfService;
import com.github.rogerli.utils.MessageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping(SysConfController.CONTENT_PATH)
@Api(tags = "SYS系统参数配置管理")
@ApiIgnore
public class SysConfController extends AbstractRestController<Conf, String> {

    public static final String CONTENT_PATH = "sys/conf";

    @Autowired
    private ConfService confService;

    @Override
    protected ServiceInterface getService() {
        return confService;
    }

    @Override
    protected String getContentPath() {
        return CONTENT_PATH;
    }

    /**
     * 删除系统配置
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(
            name = "删除系统配置",
            value = {"/delete"},
            method = {RequestMethod.POST},
            produces = {"application/json"}
    )
    @ApiOperation("删除系统配置")
    public Result delete(@ApiParam("删除系统配置参数") @RequestBody Conf entity) {
        return super.delete(entity);
    }

    /**
     * 添加系统配置
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "添加系统配置",
            value = {"/add"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("添加系统配置")
    public Result add(@ApiParam("添加系统配置参数") @RequestBody Conf entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.add(entity, bindingResult);
    }

    /**
     * 保存系统配置
     *
     * @param entity
     * @param bindingResult
     *
     * @return
     *
     * @throws IllegalValidateException
     */
    @RequestMapping(
            name = "保存系统配置",
            value = {"/save"},
            method = {RequestMethod.POST},
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ApiOperation("保存系统配置")
    public Result save(@ApiParam("保存系统配置参数") @RequestBody Conf entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(entity, bindingResult);
    }

    /**
     * 根据id查询单个系统配置项
     *
     * @param id
     *
     * @return
     */
    @RequestMapping(
            name = "根据id查询单个系统配置项",
            value = {"/find"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("根据id查询单个系统配置项")
    public Result find(@ApiParam("系统配置id") @RequestParam String id) {
        return super.find(id);
    }

    /**
     * 根据编码查询单个系统配置项
     *
     * @param code
     *
     * @return
     */
    @RequestMapping(
            name = "根据编码查询单个系统配置项",
            value = {"/by-code"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("根据编码查询单个系统配置项")
    public Result findByType(@ApiParam("配置编码") @RequestParam String code) {
        log.debug("rest path:" + getContentPath() + "/by-code:", code);
        Conf conf = confService.findByCode(code);
        return new Result().ok(MessageUtils.get("find.ok"), null, conf);
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
    public Result page(@ApiParam("查询参数") @RequestBody ConfQuery query) {
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
    public Result select(@ApiParam("查询参数") @RequestBody ConfQuery query) {
        return super.select(query);
    }


    /**
     * 设置有效
     *
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
    public Result valid(@ApiParam("系统配置参数") @RequestBody Conf entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(new Conf().setId(entity.getId()).setValid(1), bindingResult);
    }


    /**
     * 设置无效
     *
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
    public Result invalid(@ApiParam("系统配置参数") @RequestBody Conf entity, BindingResult bindingResult) throws IllegalValidateException {
        return super.save(new Conf().setId(entity.getId()).setValid(0), bindingResult);
    }

}
