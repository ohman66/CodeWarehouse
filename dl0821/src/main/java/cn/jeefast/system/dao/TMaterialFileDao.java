package cn.jeefast.system.dao;

import cn.jeefast.system.entity.TMaterialFile;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2019-05-21
 */
public interface TMaterialFileDao extends BaseMapper<TMaterialFile> {

	List<TMaterialFile> queryPageList(Page<TMaterialFile> page, Map<String, Object> map);

	void deleteBatch(String[] ids);

}