package cn.appsys.controller.deve.devappInfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.Page;
import cn.appsys.service.deve.devAppCategory.DevAppCategoryService;
import cn.appsys.service.deve.devappinfo.DevAppInfoService;
import cn.appsys.service.deve.devdatadictionary.DevDataDictionaryService;

@Controller
@RequestMapping("devappinfo")
public class DevInfoController {
	
	@Resource(name="devUserService")
	private DevAppInfoService devAppInfoService;
	
	@Resource(name="devDataDictionaryService")
	private DevDataDictionaryService devDataDictionaryService;
	
	@Resource(name="devAppCategoryService")
	private DevAppCategoryService devAppCategoryService;
	
	//分页并按照条件查询信息
		@RequestMapping("/develist")
		public String Develist(Model model,
				@RequestParam(value="querySoftwareName",required = false)String querySoftwareName,
		        @RequestParam(value="queryStatus",required=false)Integer queryStatus,
				@RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,
				@RequestParam(value="queryCategoryLevel1",required=false)Integer queryCategoryLevel1,
				@RequestParam(value="queryCategoryLevel2",required=false)Integer queryCategoryLevel2,
				@RequestParam(value="queryCategoryLevel3",required=false)Integer queryCategoryLevel3,
				@RequestParam(value="currPageNo",required=false)Integer currPageNo){
			//获取分页所需要的总记录数
			int totalCount = devAppInfoService.queryCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
			//每页显示5条
			int pageSize = 5;
			//当前页码
			currPageNo = currPageNo != null ? currPageNo : 1;
			Page page = new Page();
			page.setCurrPageNo(currPageNo);
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);
			//获取分页sql所需要的SQL对象
			int from = (currPageNo-1)*pageSize;
			//分页查询APP列表信息
			List<AppInfo> appInfoList = devAppInfoService.queryAppPageInfo(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, from, pageSize);
			//APP状态表
			List<DataDictionary> statusList = devDataDictionaryService.queryByTypeCode("APP_STATUS");
			//所属平台表
			List<DataDictionary> flatFormList = devDataDictionaryService.queryByTypeCode("APP_FLATFORM");
			//一级
			List<AppCategory> categoryLevel1List = devAppCategoryService.queryById(null);
			//二级
			List<AppCategory> categoryLevel2List = null;
			//三级
			List<AppCategory> categoryLevel3List = null;
			if (queryCategoryLevel2 != null && queryCategoryLevel2 != 0) {
				//查询二级分类信息
				categoryLevel2List = devAppCategoryService.queryById(queryCategoryLevel2);
			    model.addAttribute("categoryLevel2List", categoryLevel2List);
			}
			if (queryCategoryLevel3 != null && queryCategoryLevel3 != 0) {
				//查询三级分类信息
				categoryLevel3List = devAppCategoryService.queryById(queryCategoryLevel3);
			    model.addAttribute("categoryLevel3List", categoryLevel3List);
			}
			model.addAttribute("statusList", statusList);
			model.addAttribute("appInfoList",appInfoList);
			model.addAttribute("flatFormList",flatFormList );
			model.addAttribute("categoryLevel1List",categoryLevel1List );
			model.addAttribute("categoryLevel2List", categoryLevel2List);
			model.addAttribute("categoryLevel3List", categoryLevel3List);
			model.addAttribute("page",page );
			//将查询条件
			model.addAttribute("statusList", statusList);
			model.addAttribute("appInfoList",appInfoList);
			model.addAttribute("flatFormList",flatFormList );
			model.addAttribute("categoryLevel1List",categoryLevel1List );
			model.addAttribute("categoryLevel2List", categoryLevel2List);
			model.addAttribute("categoryLevel3List", categoryLevel3List);
			model.addAttribute("page",page );
			
			return "developer/appinfolist";
		}
	
}
