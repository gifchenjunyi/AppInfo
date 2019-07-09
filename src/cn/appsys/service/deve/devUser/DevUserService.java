package cn.appsys.service.deve.devUser;

import cn.appsys.pojo.DevUser;


public interface DevUserService {
	    //登录
		DevUser DevLogin(String devCode,String devPassword);
}
