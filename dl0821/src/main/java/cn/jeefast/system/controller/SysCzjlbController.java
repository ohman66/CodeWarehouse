package cn.jeefast.system.controller;


import cn.jeefast.common.annotation.Log;
import cn.jeefast.common.utils.Query;
import cn.jeefast.common.utils.R;
import cn.jeefast.common.validator.ValidatorUtils;
import cn.jeefast.system.entity.*;
import cn.jeefast.system.service.SysCzjlbService;
import cn.jeefast.system.service.SysUserTokenService;
import cn.jeefast.system.service.TMaterialFileService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import cn.jeefast.common.base.BaseController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 充值记录管理 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/sysCzjlb")
public class SysCzjlbController extends BaseController {
    @Autowired
    private SysCzjlbService sysCzjlbService;


    /**
     * 充值记录管理
     * 分页查询所有充值记录
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:czjlb:list")//权限校验
    public R list(@RequestParam Map<String, Object> params) throws UnknownHostException {
        //查询列表数据
        Query query = new Query(params);
        Page<SysCzjlb> pageUtil = new Page<SysCzjlb>(query.getPage(), query.getLimit());
        Page<SysCzjlb> page = sysCzjlbService.queryPageList(pageUtil, query);
        return R.ok().put("page", page);
    }

    /**
     * 
     * 根据id查询充值记录
     */
    @RequestMapping("/info/{czjlbId}")
    @RequiresPermissions("sys:czjlb:info")  
    public R info(@PathVariable("czjlbId") String czjlbId) {    //映射 URL 绑定的占位符
        SysCzjlb czjlb = sysCzjlbService.selectById(czjlbId);
        return R.ok().put("czjlb", czjlb);
    }

    /**
     * 插入一条充值记录
     */
    @Log("保存充值记录管理")
    @RequestMapping("/save")
    @RequiresPermissions("sys:czjlb:save")
    public R save(@RequestBody SysCzjlb czjlb) {    //接收post方式传递进来的参数
        ValidatorUtils.validateEntity(czjlb);   //校验
        czjlb.setCreatetime(new Date());
        czjlb.setCreateuser(getUser().getUsername());
        czjlb.setUpdatetime(new Date());
        czjlb.setUpdateuser(getUser().getUsername());
        sysCzjlbService.insert(czjlb);
        return R.ok();
    }

    /**
     * 
     * 根据 ID 修改 一条充值记录
     */
    @Log("修改充值记录管理")
    @RequestMapping("/update")
    @RequiresPermissions("sys:czjlb:update")    //权限校验
    public R update(@RequestBody SysCzjlb czjlb) {
        ValidatorUtils.validateEntity(czjlb);   //接收post方式传递进来的参数
        czjlb.setUpdatetime(new Date());     //校验
        czjlb.setUpdateuser(getUser().getUsername());
        sysCzjlbService.updateById(czjlb);
        return R.ok();
    }

    /**
     * 
     * 删除（根据ID 批量删除） 充值记录
     */
    @Log("删除充值记录管理")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:czjlb:delete")
    public R delete(@RequestBody String[] czjlbIds) {
        sysCzjlbService.deleteBatch(czjlbIds);
        return R.ok();
    }
}
