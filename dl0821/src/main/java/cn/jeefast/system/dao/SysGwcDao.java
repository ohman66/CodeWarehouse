package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysGwc;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 收藏管理 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2021-12-22
 */
public interface SysGwcDao extends BaseMapper<SysGwc> {

    List<SysGwc> queryPageList(Page<SysGwc> page, Map<String, Object> map);

    int deleteBatch(Object[] id);

}