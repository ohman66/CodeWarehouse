package cn.jeefast.system.service;

import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.entity.SysYhqbgl;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户钱包管理 服务类
 * </p>
 *
 * @author theodo
 * @since 2022-10-10
 */
public interface SysYhqbglService extends IService<SysYhqbgl> {
    Page<SysYhqbgl> queryPageList(Page<SysYhqbgl> page, Map<String, Object> map);
    void deleteBatch(String[] ids);
}
