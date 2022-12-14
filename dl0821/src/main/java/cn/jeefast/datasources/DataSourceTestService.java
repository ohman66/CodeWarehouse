package cn.jeefast.datasources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jeefast.datasources.annotation.DataSource;
import cn.jeefast.modules.api.entity.TbUser;
import cn.jeefast.modules.api.service.TbUserService;

/**
 * 测试
 */
@Service
public class DataSourceTestService {
    @Autowired
    private TbUserService userService;

    public TbUser queryObject(Long userId){
        return userService.selectById(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public TbUser queryObject2(Long userId){
        return userService.selectById(userId);
    }
}
