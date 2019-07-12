package cn.appsys.dao.deve.devdatedictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DevDateDictionaryMapper {
     /**
      * 通过类型去查找数据字典的相关信息
      */
	List<DataDictionary> queryByTypeCode(@Param("typeCode")String typeCode);
}
