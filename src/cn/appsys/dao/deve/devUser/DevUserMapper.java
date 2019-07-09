package cn.appsys.dao.deve.devUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;


public interface DevUserMapper {
	//登录
	DevUser DevLogin(@Param("devCode") String devCode,@Param("devPassword") String devPassword);
	
	
	
}
