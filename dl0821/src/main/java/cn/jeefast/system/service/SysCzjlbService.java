package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.entity.SysPjinfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 充值记录管理 服务类
 * </p>
 *
 * @author theodo
 * @since 2022-10-10
 */
public interface SysCzjlbService extends IService<SysCzjlb> {
    Page<SysCzjlb> queryPageList(Page<SysCzjlb> page, Map<String, Object> map);
    void deleteBatch(String[] ids);
}
