package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysFood;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物品信息表 服务类
 * </p>
 *
 */
public interface SysFoodService extends IService<SysFood> {

    Page<SysFood> queryPageList(Page<SysFood> page, Map<String, Object> map);
    void deleteBatch(String[] foodIds);

    List<SysFood> selectListByMap(Map<String,Object> map);
	
}
