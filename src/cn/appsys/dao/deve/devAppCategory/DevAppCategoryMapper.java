package cn.appsys.dao.deve.devAppCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface DevAppCategoryMapper {
    List<AppCategory> queryById(@Param("parentId")Integer parentId);

}
