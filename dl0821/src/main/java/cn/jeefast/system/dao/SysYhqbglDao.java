package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.entity.SysYhqbgl;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 用户钱包管理 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2022-10-10
 */
public interface SysYhqbglDao extends BaseMapper<SysYhqbgl> {
    List<SysYhqbgl> queryPageList(Page<SysYhqbgl> page, Map<String, Object> map);

    int deleteBatch(Object[] id);
}