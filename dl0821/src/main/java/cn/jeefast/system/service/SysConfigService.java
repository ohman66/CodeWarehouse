package cn.jeefast.system.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.jeefast.system.entity.SysConfig;

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 */
public interface SysConfigService extends IService<SysConfig> {
	
	public Page<SysConfig> selectSysConfigList(Page<SysConfig> page, Map<String, Object> map);

	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
	public String getValue(String key);


}
