package cn.jeefast.system.controller;


import cn.jeefast.common.annotation.Log;
import cn.jeefast.common.utils.Query;
import cn.jeefast.common.utils.R;
import cn.jeefast.common.validator.ValidatorUtils;
import cn.jeefast.system.entity.SysYhqbgl;
import cn.jeefast.system.service.SysYhqbglService;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.jeefast.common.base.BaseController;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户钱包管理 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/sysYhqbgl")
public class SysYhqbglController extends BaseController {
    @Autowired
    private SysYhqbglService sysYhqbglService;


    /**
     * 用户钱包管理
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:yhqbgl:list")
    public R list(@RequestParam Map<String, Object> params) throws UnknownHostException {
        //查询列表数据
        Query query = new Query(params);
        Page<SysYhqbgl> pageUtil = new Page<SysYhqbgl>(query.getPage(), query.getLimit());
        Page<SysYhqbgl> page = sysYhqbglService.queryPageList(pageUtil, query);
        return R.ok().put("page", page);
    }

    /**
     * 用户钱包管理信息
     */
    @RequestMapping("/info/{yhqbglId}")
    @RequiresPermissions("sys:yhqbgl:info")
    public R info(@PathVariable("yhqbglId") String yhqbglId) {
        SysYhqbgl yhqbgl = sysYhqbglService.selectById(yhqbglId);
        return R.ok().put("yhqbgl", yhqbgl);
    }

    /**
     * 保存用户钱包管理
     */
    @Log("保存用户钱包管理")
    @RequestMapping("/save")
    @RequiresPermissions("sys:yhqbgl:save")
    public R save(@RequestBody SysYhqbgl yhqbgl) {
        ValidatorUtils.validateEntity(yhqbgl);
        yhqbgl.setCreatetime(new Date());
        yhqbgl.setCreateuser(getUser().getUsername());
        yhqbgl.setUpdatetime(new Date());
        yhqbgl.setUpdateuser(getUser().getUsername());
        sysYhqbglService.insert(yhqbgl);
        return R.ok();
    }

    /**
     * 修改用户钱包管理
     */
    @Log("修改用户钱包管理")
    @RequestMapping("/update")
    @RequiresPermissions("sys:yhqbgl:update")
    public R update(@RequestBody SysYhqbgl yhqbgl) {
        ValidatorUtils.validateEntity(yhqbgl);
        yhqbgl.setUpdatetime(new Date());
        yhqbgl.setUpdateuser(getUser().getUsername());
        sysYhqbglService.updateById(yhqbgl);
        return R.ok();
    }

    /**
     * 删除用户钱包管理
     */
    @Log("删除用户钱包管理")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:yhqbgl:delete")
    public R delete(@RequestBody String[] yhqbglIds) {
        sysYhqbglService.deleteBatch(yhqbglIds);
        return R.ok();
    }
}
