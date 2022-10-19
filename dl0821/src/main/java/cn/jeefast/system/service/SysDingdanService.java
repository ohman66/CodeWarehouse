package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysDingdan;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface SysDingdanService extends IService<SysDingdan> {

    Page<SysDingdan> queryPageList(Page<SysDingdan> page, Map<String, Object> map);
    void deleteBatch(String[] dingdanIds);

    Page<Map<String,Object>> queryPageListByYye(Page<Map<String, Object>> page, Map<String, Object> map);
}
