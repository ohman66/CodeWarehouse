package cn.jeefast.system.controller;


import cn.jeefast.common.annotation.Log;
import cn.jeefast.common.base.BaseController;
import cn.jeefast.common.utils.Query;
import cn.jeefast.common.utils.R;
import cn.jeefast.common.validator.ValidatorUtils;
import cn.jeefast.system.entity.*;
import cn.jeefast.system.service.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  租赁管理 前端控制器
 * </p>
 *
 * @author theodo
 * @since 2021-12-06
 */
@RestController
@RequestMapping("/sysDingdan")
public class SysDingdanController extends BaseController {

    @Autowired
    private SysDingdanService sysDingdanService;

    @Autowired
    private SysFoodService sysFoodService;

    @Autowired
    private SysReserveService sysReserveService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysYhqbglService sysYhqbglService;

    /**
     * 租赁订单列表,分页查询
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dingdan:list")      // @RequiresPermissions 权限验证
    public R list(@RequestParam Map<String, Object> params) {
        if(getUser().getUsername() != "admin"){
            params.put("sssj",getUser().getUsername());
        }
        //查询列表数据
        Query query = new Query(params);
        Page<SysDingdan> pageUtil = new Page<SysDingdan>(query.getPage(), query.getLimit());
        Page<SysDingdan> page = sysDingdanService.queryPageList(pageUtil, query);
        if(page.getRecords().size()>0){
            for(int i =0;i<page.getRecords().size();i++){
                SysDingdan sysDingdan = page.getRecords().get(i);
                if(sysDingdan.getDdtype() != null && sysDingdan.getDdtype().equals("2")){
                    SysReserve sysReserve = sysReserveService.selectById(sysDingdan.getParintid());
                    if(sysReserve != null){
                        sysDingdan.setName(sysReserve.getBjh());
                    }
                }
                if(sysDingdan.getDdtype() != null && sysDingdan.getDdtype().equals("1")){
                    SysFood sysFood = sysFoodService.selectById(sysDingdan.getParintid());
                    if(sysFood != null){
                        sysDingdan.setName(sysFood.getFoodname());
                    }
                }
            }
        }
        return R.ok().put("page", page);
    }

    /**
     * 租赁管理列表,分页查询
     */
    @RequestMapping("/yyelist")
    public R yyelist(@RequestParam Map<String, Object> params) {

        if(getUser().getUsername() != "admin"){
            params.put("sssj",getUser().getUsername());
        }
        if(params.get("kssj") != null && !params.get("kssj").equals("")){
            params.put("kssj",params.get("kssj").toString().substring(0,10));
        }
        System.out.println("paramsparamsparamsparamsparams"+params);
        //查询列表数据
        Query query = new Query(params);
        Page<Map<String,Object>> pageUtil = new Page<Map<String,Object>>(query.getPage(), query.getLimit());
        Page<Map<String,Object>> page = sysDingdanService.queryPageListByYye(pageUtil, query);
        return R.ok().put("page", page);
    }

    /**
     * 根据id查询订单
     */
    @RequestMapping("/info/{dingdanId}")
    @RequiresPermissions("sys:dingdan:info")
    public R info(@PathVariable("dingdanId") String dingdanId) {
        SysDingdan dingdan = sysDingdanService.selectById(dingdanId);

        return R.ok().put("dingdan", dingdan);
    }

    /**
     * 根据id查询订单
     */
    @RequestMapping("/infoprent/{dingdanId}")
    @RequiresPermissions("sys:dingdan:infoprent")
    public R infoprent(@PathVariable("dingdanId") String dingdanId) {
        SysDingdan dingdan = sysDingdanService.selectById(dingdanId);
        List<SysDingdan> sysDingdanList = new ArrayList<>();

        return R.ok().put("dingdan", dingdan).put("allFiles",sysDingdanList);
    }

    /**
     * 插入一条订单信息
     */
    @Log("保存租赁管理")
    @RequestMapping("/save")
    @RequiresPermissions("sys:dingdan:save")
    public R save(@RequestBody SysDingdan dingdan) {
        ValidatorUtils.validateEntity(dingdan);
        dingdan.setUpdatetime(new Date());
        sysDingdanService.insert(dingdan);
        return R.ok();
    }

    /**
     * 修改订单
     */
    @Log("修改租赁管理")
    @RequestMapping("/update")
    @RequiresPermissions("sys:dingdan:update")
    public R update(@RequestBody SysDingdan dingdan) {
        ValidatorUtils.validateEntity(dingdan);  //对订单的格式进行校验
        dingdan.setUpdatetime(new Date());  
        sysDingdanService.updateById(dingdan);

        return R.ok();
    }


    /**
     * 删除（根据ID 批量删除）订单
     */
    @Log("删除租赁管理")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dingdan:delete")
    public R delete(@RequestBody String[] dingdanIds) {
        sysDingdanService.deleteBatch(dingdanIds);
        return R.ok();
    }

    /**
     * 审批租赁管理
     */
    @Log("审批租赁管理")
    @RequestMapping("/bzjthsp")
    @RequiresPermissions("sys:dingdan:bzjthsp")
    public R bzjthsp(@RequestBody JSONObject param) {
        System.out.println("paramparamparam"+param);
        JSONArray ids = param.getJSONArray("ids");
        String bzjthsq = param.getString("bzjthsq");
        if(ids.size()>0){
            for (int i = 0; i < ids.size(); i++) {
                SysDingdan sysDingdan = sysDingdanService.selectById(ids.get(i).toString());
                sysDingdan.setBzjthsq(bzjthsq);
                sysDingdanService.updateById(sysDingdan);
                if(bzjthsq.equals("1")){
                    SysFood sysFood = sysFoodService.selectById(sysDingdan.getParintid());
                    SysYhqbgl sysYhqbgl = sysYhqbglService.selectOne(new EntityWrapper<SysYhqbgl>().eq("username",sysDingdan.getYdry()));
                    sysYhqbgl.setYue(sysYhqbgl.getYue()+sysFood.getBzj());
                    sysYhqbglService.updateById(sysYhqbgl);
                }

            }
        }
        return R.ok();
    }

	
}
