package cn.jeefast.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import cn.jeefast.system.entity.SysDept;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 */
public interface SysDeptService extends IService<SysDept> {
	List<SysDept> queryList(Map<String, Object> map);
	
	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	public List<Long> queryDetpIdList(Long parentId);
	
	/**
	 * 获取子部门ID(包含本部门ID)，用于数据过滤
	 */
	String getSubDeptIdList(Long deptId);
}
