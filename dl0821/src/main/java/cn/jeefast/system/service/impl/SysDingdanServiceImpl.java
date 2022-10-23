package cn.jeefast.system.service.impl;

import cn.jeefast.system.dao.SysFoodDao;
import cn.jeefast.system.entity.SysDingdan;
import cn.jeefast.system.dao.SysDingdanDao;
import cn.jeefast.system.entity.SysFood;
import cn.jeefast.system.service.SysDingdanService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class SysDingdanServiceImpl extends ServiceImpl<SysDingdanDao, SysDingdan> implements SysDingdanService {

    @Autowired
    private SysDingdanDao sysDingdanDao;


    @Override
    public Page<SysDingdan> queryPageList(Page<SysDingdan> page, Map<String, Object> map) {
        page.setRecords(sysDingdanDao.queryPageList(page, map));
        return page;
    }

    public Page<Map<String,Object>> queryPageListByYye(Page<Map<String,Object>> page, Map<String, Object> map){
        List<Map<String,Object>> mapList = sysDingdanDao.queryPageListByYye(map);
        page.setRecords(mapList);
        return page;
    }

    @Override
    public void deleteBatch(String[] dingdanIds) {
        sysDingdanDao.deleteBatch(dingdanIds);
    }
}
