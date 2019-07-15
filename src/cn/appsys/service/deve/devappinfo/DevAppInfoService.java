package cn.appsys.service.deve.devappinfo;

import java.util.List;






import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface DevAppInfoService {
	/**
	 * 分页查询APP信息列表
	 */
     List<AppInfo> queryAppPageInfo(String querySoftwareName,Integer queryStatus,
    	Integer queryFlatformId,Integer queryCategoryLevel1,
    	Integer queryCategoryLevel2,Integer queryCategoryLevel3,
    	Integer from,Integer pageSize);
    
     
    /**
     * 查询总记录数
     */
     int queryCount(String querySoftwareName,Integer queryStatus,
    	    	Integer queryFlatformId,Integer queryCategoryLevel1,
    	    	Integer queryCategoryLevel2,Integer queryCategoryLevel3);
     

     /**
      * 新增App信息
      * @return
      */
     int devAddApp(AppInfo appInfo);
     
     
     
     /**
      * 删除APP
      * @return
      */
     int devDeleteApp(Integer id);
     
     
     /**
      * 查看APP
      */
     AppInfo devgetAppPageInfo(Integer id);
     
     
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
 	AppInfo queryInfoByIdAndAPK(Integer id,String APKName);
 	
 	
 	/**
  	 * 修改状态
  	 * @param appInfo
  	 * @return
  	 */
  	int updateStatus(AppInfo appInfo);
 
}
