package cn.jeefast.system.service.impl;

import cn.jeefast.system.dao.SysPjinfoDao;
import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.dao.SysCzjlbDao;
import cn.jeefast.system.entity.SysPjinfo;
import cn.jeefast.system.service.SysCzjlbService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 充值记录管理 服务实现类
 * </p>
 *
 */
@Service
public class SysCzjlbServiceImpl extends ServiceImpl<SysCzjlbDao, SysCzjlb> implements SysCzjlbService {

    @Autowired
    private SysCzjlbDao sysCzjlbDao;

    @Override
    public Page<SysCzjlb> queryPageList(Page<SysCzjlb> page, Map<String, Object> map) {
        page.setRecords(sysCzjlbDao.queryPageList(page, map));
        return page;
    }

    @Override
    public void deleteBatch(String[] ids) {
        sysCzjlbDao.deleteBatch(ids);
    }

}
