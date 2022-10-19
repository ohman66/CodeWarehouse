package cn.jeefast.system.dao;

import cn.jeefast.system.entity.SysDingdan;
import cn.jeefast.system.entity.SysFood;
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
 * @since 2021-12-06
 */
public interface SysDingdanDao extends BaseMapper<SysDingdan> {

    List<SysDingdan> queryPageList(Page<SysDingdan> page, Map<String, Object> map);

    int deleteBatch(Object[] id);

    List<Map<String,Object>> queryPageListByYye(Map<String, Object> map);

}