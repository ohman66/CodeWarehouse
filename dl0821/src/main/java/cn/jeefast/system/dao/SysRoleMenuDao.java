package cn.jeefast.system.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.jeefast.system.entity.SysRoleMenu;

/**
 * <p>
  * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
	
	void save(Map<String, Object> map);

	void deleteByRoleId(Long roleId);
	
}