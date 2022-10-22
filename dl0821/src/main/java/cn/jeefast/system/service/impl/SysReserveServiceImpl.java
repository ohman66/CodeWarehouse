package cn.jeefast.system.service.impl;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.entity.SysReserve;
import cn.jeefast.system.dao.SysReserveDao;
import cn.jeefast.system.service.SysReserveService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 预定餐位 服务实现类
 * </p>
 *
 */
@Service
public class SysReserveServiceImpl extends ServiceImpl<SysReserveDao, SysReserve> implements SysReserveService {


    @Autowired
    private SysReserveDao sysReserveDao;

    @Override
    public Page<SysReserve> queryPageList(Page<SysReserve> page, Map<String, Object> map) {
        page.setRecords(sysReserveDao.queryPageList(page, map));
        return page;
    }

    @Override
    public void deleteBatch(String[] reserveIds) {
        sysReserveDao.deleteBatch(reserveIds);
    }
}
