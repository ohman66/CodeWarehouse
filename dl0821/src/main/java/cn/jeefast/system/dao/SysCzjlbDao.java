package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.entity.SysPjinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 充值记录管理 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2022-10-10
 */
public interface SysCzjlbDao extends BaseMapper<SysCzjlb> {
    List<SysCzjlb> queryPageList(Page<SysCzjlb> page, Map<String, Object> map);

    int deleteBatch(Object[] id);
}