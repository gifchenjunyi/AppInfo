package cn.appsys.service.deve.devappversion.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import cn.appsys.pojo.AppVersion;
import cn.appsys.dao.deve.appversion.*;
import cn.appsys.service.deve.devappversion.DevVersionService;

@Service("devVersionService")
public class DevVersionServiceImpl implements DevVersionService {
	 @Resource(name="devAppVersionMapper")
     private DevAppVersionMapper devAppVersionMapper ;
	
	/**
	 * 增加版本信息
	 */
	public int devAppVersion(AppVersion appVersion) {
		return devAppVersionMapper.devAppVersion(appVersion);
	}


	/**
	 * 查询指定APP版本信息
	 * @param id
	 * @return
	 */
	public List<AppVersion> getAppVersionlist(Integer id) {
		return devAppVersionMapper.getAppVersionlist(id);
	}


	@Override
	public AppVersion getAppVersionByVid(Integer vid) {
		return devAppVersionMapper.getAppVersionByVid(vid);
	}


	@Override
	public boolean deleteApkFile(Integer id) {
	int result =devAppVersionMapper.deleteApkFile(id);
	if(result > 0){
		return true;
	}else {
		return false;
	}
	}


	/**
	 * 修改版本信息
	 */
	public int modifyAppVersion(AppVersion appVersion) {
		return devAppVersionMapper.modifyAppVersion(appVersion);
	}
	
	 

}
