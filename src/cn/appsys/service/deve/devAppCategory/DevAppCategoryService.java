package cn.appsys.service.deve.devAppCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface DevAppCategoryService {
	   List<AppCategory> queryById(@Param("parentId")Integer parentId);
}
