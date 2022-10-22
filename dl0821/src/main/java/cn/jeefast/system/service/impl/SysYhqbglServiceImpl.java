package cn.jeefast.system.service.impl;

import cn.jeefast.system.dao.SysCzjlbDao;
import cn.jeefast.system.entity.SysCzjlb;
import cn.jeefast.system.entity.SysYhqbgl;
import cn.jeefast.system.dao.SysYhqbglDao;
import cn.jeefast.system.service.SysYhqbglService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户钱包管理 服务实现类
 * </p>
 *
 */
@Service
public class SysYhqbglServiceImpl extends ServiceImpl<SysYhqbglDao, SysYhqbgl> implements SysYhqbglService {

    @Autowired
    private SysYhqbglDao sysYhqbglDao;

    @Override
    public Page<SysYhqbgl> queryPageList(Page<SysYhqbgl> page, Map<String, Object> map) {
        page.setRecords(sysYhqbglDao.queryPageList(page, map));
        return page;
    }

    @Override
    public void deleteBatch(String[] ids) {
        sysYhqbglDao.deleteBatch(ids);
    }

}
