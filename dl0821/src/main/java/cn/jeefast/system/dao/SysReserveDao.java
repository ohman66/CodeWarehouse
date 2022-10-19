package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysReserve;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 预定餐位 Mapper 接口
 * </p>
 *
 */
public interface SysReserveDao extends BaseMapper<SysReserve> {

    List<SysReserve> queryPageList(Page<SysReserve> page, Map<String, Object> map);

    int deleteBatch(Object[] id);

}