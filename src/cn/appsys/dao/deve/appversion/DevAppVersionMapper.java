package cn.appsys.dao.deve.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;

public interface DevAppVersionMapper {
	/**
	 * 增加版本信息
	 * @return
	 */
	int devAppVersion(AppVersion appVersion);
	
	
	/**
	 * 通过app版本id查询app版本的详情对象信息
	 * @param vid 
	 * @return
	 */
	AppVersion getAppVersionByVid(@Param("vid")Integer vid);

	
	
	/**
	 * 查询指定APP版本信息
	 * @param id
	 * @return
	 */
	List<AppVersion> getAppVersionlist(@Param("id")Integer id);
	
	
	/**
	 * 根据app版本id，将字段apkLocPath、apkFileName、downloadLink这三个字段给清空。
	 * @param id app版本id
	 * @return 受影响的行数
	 */
	 int deleteApkFile(@Param("id")Integer id);
	 
	 /**
	  * 修改版本信息
	  * @param appVersion
	  * @return
	  */
	 int modifyAppVersion(AppVersion appVersion);
	 
	 
    

}
