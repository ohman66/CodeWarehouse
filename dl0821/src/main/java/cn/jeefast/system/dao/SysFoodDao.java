package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysPjinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 物品信息表 Mapper 接口
 * </p>
 *
 */
public interface SysFoodDao extends BaseMapper<SysFood> {

    List<SysFood> queryPageList(Page<SysFood> page, Map<String, Object> map);

    int deleteBatch(Object[] id);
    List<SysFood> selectListByMap(Map<String, Object> map);
}