package cn.jeefast.system.service.impl;

import cn.jeefast.system.dao.SysConfigDao;
import cn.jeefast.system.entity.SysConfig;
import cn.jeefast.system.redis.SysConfigRedis;
import cn.jeefast.system.service.SysConfigService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {
	
	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private SysConfigRedis sysConfigRedis;
	
	public Page<SysConfig> selectSysConfigList(Page<SysConfig> page, Map<String, Object> map) {
	    page.setRecords(sysConfigDao.selectSysConfigList(page, map));
	    return page;
	}

	@Override
	@Transactional
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}
	
	@Override
	public String getValue(String key) {
		SysConfig config = sysConfigRedis.get(key);
		if(config == null){
			config = sysConfigDao.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getValue();
	}


	
}
