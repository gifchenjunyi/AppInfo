package cn.appsys.service.deve.devdatadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DevDataDictionaryService {
	 /**
     * 通过类型去查找数据字典的相关信息
     */
	List<DataDictionary> queryByTypeCode(String typeCode);
}
