package cn.appsys.dao.deve.devappinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface DevAppInfoMapper {
	/**
	 * 分页查询APP信息列表
	 */
     List<AppInfo> queryAppPageInfo(
    	@Param("querySoftwareName")String querySoftwareName,	 
    	@Param("queryStatus")Integer queryStatus,
    	@Param("queryFlatformId")Integer queryFlatformId,
    	@Param("queryCategoryLevel1")Integer queryCategoryLevel1,
    	@Param("queryCategoryLevel2")Integer queryCategoryLevel2,
    	@Param("queryCategoryLevel3")Integer queryCategoryLevel3,
    	@Param("from")Integer from,
    	@Param("pageSize")Integer pageSize);
    
     
    /**
     * 查询总记录数
     */
     int queryCount(
    		@Param("querySoftwareName")String querySoftwareName,	 
    	    @Param("queryStatus")Integer queryStatus,
    	    @Param("queryFlatformId")Integer queryFlatformId,
    	    @Param("queryCategoryLevel1")Integer queryCategoryLevel1,
    	    @Param("queryCategoryLevel2")Integer queryCategoryLevel2,
    	    @Param("queryCategoryLevel3")Integer queryCategoryLevel3); 
     
     
     /**
      * 新增App信息
      * @return
      */
     int devAddApp(AppInfo appInfo);
     
     
     /**
      * 删除APP
      * @return
      */
     int devDeleteApp(@Param("id")Integer id);
     
     
     /**
      * 查看APP
      */
     AppInfo devgetAppPageInfo(@Param("id")Integer id);
     
     
     /**
      * 修改APP
      */
     int devUpdateAppInfo(AppInfo appInfo);  
     
     
     /**
  	 * 通过appinfo表的主键id与APKName字段查询app详情信息。
  	 * @param id 主键id
  	 * @param APKName
  	 * @return app详情信息。
  	 */
  	AppInfo queryInfoByIdAndAPK(@Param("id") Integer id,@Param("APKName") String APKName);
    		
}
