package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysReserve;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 预定餐位 服务类
 * </p>
 *
 */
public interface SysReserveService extends IService<SysReserve> {

    Page<SysReserve> queryPageList(Page<SysReserve> page, Map<String, Object> map);
    void deleteBatch(String[] reserveIds);
	
}
