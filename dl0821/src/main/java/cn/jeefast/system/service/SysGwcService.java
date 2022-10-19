package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysGwc;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 收藏管理 服务类
 * </p>
 *
 */
public interface SysGwcService extends IService<SysGwc> {

    Page<SysGwc> queryPageList(Page<SysGwc> page, Map<String, Object> map);
    void deleteBatch(String[] gwcIds);
	
}
