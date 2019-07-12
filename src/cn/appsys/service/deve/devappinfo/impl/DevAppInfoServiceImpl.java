package cn.appsys.service.deve.devappinfo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.devUser.DevUserMapper;
import cn.appsys.dao.deve.devappinfo.DevAppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.deve.devappinfo.DevAppInfoService;

@Service("devappInfoService")
public class DevAppInfoServiceImpl implements DevAppInfoService {
	@Resource(name="devAppInfoMapper")
	private DevAppInfoMapper devAppInfoMapper;
	

	/**
	 * 分页显示
	 */
	public List<AppInfo> queryAppPageInfo(String querySoftwareName,
			Integer queryStatus, Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer from, Integer pageSize) {
		return devAppInfoMapper.queryAppPageInfo(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, pageSize);
	}

	/**
	 * 获得总记录数
	 */
	public int queryCount(String querySoftwareName, Integer queryStatus,
			Integer queryFlatformId, Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
		return devAppInfoMapper.queryCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
	}

}