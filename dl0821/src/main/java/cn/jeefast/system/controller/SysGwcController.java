package cn.jeefast.system.controller;


import cn.jeefast.common.annotation.Log;
import cn.jeefast.common.base.BaseController;
import cn.jeefast.common.utils.Query;
import cn.jeefast.common.utils.R;
import cn.jeefast.system.entity.SysGwc;
import cn.jeefast.system.entity.TMaterialFile;
import cn.jeefast.system.service.SysGwcService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收藏管理 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/sysGwc")
public class SysGwcController extends BaseController {

    @Autowired
    private SysGwcService sysGwcService;

    /**
     * 收藏信息列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:gwc:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Page<SysGwc> pageUtil = new Page<SysGwc>(query.getPage(), query.getLimit());
        Page<SysGwc> page = sysGwcService.queryPageList(pageUtil, query);
        return R.ok().put("page", page);
    }

    /**
     * 收藏信息信息
     */
    @RequestMapping("/info/{gwcId}")
    @RequiresPermissions("sys:gwc:info")
    public R info(@PathVariable("gwcId") String gwcId) throws UnknownHostException {
        SysGwc gwc = sysGwcService.selectById(gwcId);

        //获取附件列表
//        List<TMaterialFile> tMaterialFiles = tMaterialFileService.selectList(new EntityWrapper<TMaterialFile>().eq("parentid",gwc.getId()));
//        List<Map<String,Object>> mapList = new ArrayList<>();
//        if(!tMaterialFiles.isEmpty()){
//            for(TMaterialFile tMaterialFile:tMaterialFiles){
//                Map<String,Object> map =new HashMap<>();
//                map.put("id",tMaterialFile.getId());
//                map.put("filePath",tMaterialFile.getSfilename());
//                map.put("fileName",tMaterialFile.getSaccessoryname());
//                mapList.add(map);
//            }
//
//        }
//        JSONArray json = (JSONArray) JSONArray.toJSON(mapList);
//
//        gwc.setFiles(json);
        return R.ok().put("gwc", gwc);
    }

    /**
     * 删除收藏信息
     */
    @Log("删除收藏信息")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:gwc:delete")
    public R delete(@RequestBody String[] gwcIds) {
        sysGwcService.deleteBatch(gwcIds);
        return R.ok();
    }
	
}
