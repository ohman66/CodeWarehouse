package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysPjinfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2021-10-22
 */
public interface SysPjinfoDao extends BaseMapper<SysPjinfo> {
    List<SysPjinfo> queryPageList(Page<SysPjinfo> page, Map<String, Object> map);

    int deleteBatch(Object[] id);

}