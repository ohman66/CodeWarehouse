package cn.jeefast.system.controller;


import cn.jeefast.common.annotation.Log;
import cn.jeefast.common.utils.Query;
import cn.jeefast.common.utils.R;
import cn.jeefast.common.validator.ValidatorUtils;
import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysPjinfo;
import cn.jeefast.system.entity.TMaterialFile;
import cn.jeefast.system.service.SysFoodService;
import cn.jeefast.system.service.TMaterialFileService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import cn.jeefast.common.base.BaseController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * <p>
 * 物品信息表 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/sysFood")
public class SysFoodController extends BaseController {


    @Autowired
    private SysFoodService sysFoodService;

    @Autowired
    private TMaterialFileService tMaterialFileService;

    @Value("${server.port}")
    private String serverport;

    @Value("${server.context-path}")
    private String servercontextpath;

    /**
     * 物品信息列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:food:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Page<SysFood> pageUtil = new Page<SysFood>(query.getPage(), query.getLimit());
        Page<SysFood> page = sysFoodService.queryPageList(pageUtil, query);
        return R.ok().put("page", page);
    }

    /**
     * 物品信息信息
     */
    @RequestMapping("/info/{foodId}")
    @RequiresPermissions("sys:food:info")
    public R info(@PathVariable("foodId") String foodId) throws UnknownHostException {
        SysFood food = sysFoodService.selectById(foodId);

        //获取附件列表
        List<TMaterialFile> tMaterialFiles = tMaterialFileService.selectList(new EntityWrapper<TMaterialFile>().eq("parentid",food.getId()));
        List<Map<String,Object>> mapList = new ArrayList<>();
        if(!tMaterialFiles.isEmpty()){
            for(TMaterialFile tMaterialFile:tMaterialFiles){
                Map<String,Object> map =new HashMap<>();
                map.put("id",tMaterialFile.getId());
                map.put("filePath",tMaterialFile.getSfilename());
                map.put("fileName",tMaterialFile.getSaccessoryname());
                mapList.add(map);
            }

        }
        JSONArray json = (JSONArray) JSONArray.toJSON(mapList);

        food.setFiles(json);
        return R.ok().put("food", food);
    }

    /**
     * 物品信息信息
     */
    @RequestMapping("/infoprent/{foodId}")
    @RequiresPermissions("sys:food:infoprent")
    public R infoprent(@PathVariable("foodId") String foodId) {
        SysFood food = sysFoodService.selectById(foodId);
        List<SysFood> sysFoodList = new ArrayList<>();

        return R.ok().put("food", food).put("allFiles",sysFoodList);
    }

    /**
     * 保存物品信息
     */
    @Log("保存物品信息")
    @RequestMapping("/save")
    @RequiresPermissions("sys:food:save")
    public R save(@RequestBody SysFood food) {
        ValidatorUtils.validateEntity(food);
        food.setUpdatetime(new Date());
        sysFoodService.insert(food);

        if(food.getFiles() != null){
            tMaterialFileService.setTMaterialFilePrintId(food.getFiles(),food.getId());
        }
        return R.ok();
    }

    /**
     * 修改物品信息
     */
    @Log("修改物品信息")
    @RequestMapping("/update")
    @RequiresPermissions("sys:food:update")
    public R update(@RequestBody SysFood food) {
        ValidatorUtils.validateEntity(food);
        food.setUpdatetime(new Date());
        sysFoodService.updateById(food);
        if(food.getFiles() != null){
            tMaterialFileService.setTMaterialFilePrintId(food.getFiles(),food.getId());
        }
        return R.ok();
    }


    /**
     * 删除物品信息
     */
    @Log("删除物品信息")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:food:delete")
    public R delete(@RequestBody String[] foodIds) {
        sysFoodService.deleteBatch(foodIds);
        return R.ok();
    }

    /**
     * 上下架物品信息
     */
    @Log("上下架物品信息")
    @RequestMapping("/sxj")
    @RequiresPermissions("sys:food:sxj")
    public R sj(@RequestBody JSONObject param) {
        JSONArray ids = param.getJSONArray("ids");
        String issj = param.getString("issj");
        if(ids.size()>0){
            for (int i = 0; i < ids.size(); i++) {
                SysFood sysFood = sysFoodService.selectById(ids.get(i).toString());
                sysFood.setIssj(issj);
                sysFoodService.updateById(sysFood);
            }
        }
        return R.ok();
    }

}
