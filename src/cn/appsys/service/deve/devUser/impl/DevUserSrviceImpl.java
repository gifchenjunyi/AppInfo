package cn.appsys.service.deve.devUser.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.devUser.devUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.devUser.DevUserService;

@Service("devUserService")
public class DevUserSrviceImpl implements DevUserService{
	
	@Resource(name="devUserMapper")
	private devUserMapper devUserMapper;

	/**
	 * 登录
	 */
	public DevUser DevLogin(String devCode, String devPassword) {
		
		return devUserMapper.DevLogin(devCode, devPassword);
	}
     
}
