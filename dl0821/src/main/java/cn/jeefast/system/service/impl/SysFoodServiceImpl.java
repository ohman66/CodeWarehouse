package cn.jeefast.system.service.impl;

import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.dao.SysFoodDao;
import cn.jeefast.system.entity.SysPjinfo;
import cn.jeefast.system.service.SysFoodService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 物品信息表 服务实现类
 * </p>
 *
 */
@Service
public class SysFoodServiceImpl extends ServiceImpl<SysFoodDao, SysFood> implements SysFoodService {


    @Autowired
    private SysFoodDao sysFoodDao;

    @Override
    public Page<SysFood> queryPageList(Page<SysFood> page, Map<String, Object> map) {
        page.setRecords(sysFoodDao.queryPageList(page, map));
        return page;
    }

    @Override
    public void deleteBatch(String[] foodIds) {
        sysFoodDao.deleteBatch(foodIds);
    }

    @Override
    public List<SysFood> selectListByMap(Map<String, Object> map) {
        return sysFoodDao.selectListByMap(map);
    }

}
