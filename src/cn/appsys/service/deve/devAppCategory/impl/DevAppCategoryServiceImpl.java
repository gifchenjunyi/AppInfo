package cn.appsys.service.deve.devAppCategory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.devAppCategory.DevAppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.deve.devAppCategory.DevAppCategoryService;

@Service("devAppCategoryService")
public class DevAppCategoryServiceImpl implements DevAppCategoryService {
    @Resource(name="devAppCategoryMapper")
    private DevAppCategoryMapper devAppCategoryMapper;
	
	@Override
	public List<AppCategory> queryById(Integer parentId) {
		return devAppCategoryMapper.queryById(parentId);
	}

}
