package cn.appsys.service.deve.devdatadictionary.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.deve.devdatedictionary.DevDateDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.deve.devdatadictionary.DevDataDictionaryService;

@Service("devDataDictionaryService")
public class DevDataDictionaryServiceImpl implements DevDataDictionaryService {
      @Resource(name="devDateDictionaryMapper")
      private DevDateDictionaryMapper devDateDictionaryMapper;
	
	  @Override
	  public List<DataDictionary> queryByTypeCode(String typeCode) {
		return devDateDictionaryMapper.queryByTypeCode(typeCode);
	}

}
