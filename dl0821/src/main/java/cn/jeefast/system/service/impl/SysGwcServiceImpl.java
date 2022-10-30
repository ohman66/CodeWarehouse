package cn.jeefast.system.service.impl;

import cn.jeefast.modules.api.annotation.AuthIgnore;
import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysGwc;
import cn.jeefast.system.dao.SysGwcDao;
import cn.jeefast.system.service.SysGwcService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 收藏管理 服务实现类
 * </p>
 *
 */
@Service
public class SysGwcServiceImpl extends ServiceImpl<SysGwcDao, SysGwc> implements SysGwcService {


    @Autowired
    private SysGwcDao sysGwcDao;

    @Override
    public Page<SysGwc> queryPageList(Page<SysGwc> page, Map<String, Object> map) {
        page.setRecords(sysGwcDao.queryPageList(page, map));
        return page;
    }

    @Override
    public void deleteBatch(String[] gwcIds) {
        sysGwcDao.deleteBatch(gwcIds);
    }
}
