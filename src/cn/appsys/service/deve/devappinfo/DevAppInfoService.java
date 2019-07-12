package cn.appsys.service.deve.devappinfo;

import java.util.List;


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
}
