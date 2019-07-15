package cn.appsys.controller.deve.devappInfo;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.Page;
import cn.appsys.service.deve.devAppCategory.DevAppCategoryService;
import cn.appsys.service.deve.devappinfo.DevAppInfoService;
import cn.appsys.service.deve.devdatadictionary.DevDataDictionaryService;

@Controller
@RequestMapping("/devappinfo")
public class DevInfoController {
	
	@Resource(name="devAppInfoService")
	private DevAppInfoService devAppInfoService;
	
	@Resource(name="devDataDictionaryService")
	private DevDataDictionaryService devDataDictionaryService;
	
	@Resource(name="devAppCategoryService")
	private DevAppCategoryService devAppCategoryService;
	
	
	/*验证apkName是否存在 */
	@RequestMapping(value = "/apkexist",method = RequestMethod.GET)
	@ResponseBody
	public Object apkExist(@RequestParam("APKName")String apkName) {
		AppInfo appInfo = devAppInfoService.queryInfoByIdAndAPK(null, apkName);
		Map<String, String> map = new HashMap<String, String>();
		if (apkName == null) {
			map.put("APKName", "empty");
		}else if (appInfo != null) {
			map.put("APKName", "exist");
		} else {
			map.put("APKName", "noexist");
		}
		return map;
	}

	
	
	//修改APP信息
	 @RequestMapping(value="/devappinfomodifysave",method=RequestMethod.POST)
    public String devappinfomodifysave(AppInfo appInfo,HttpServletRequest request,HttpSession session,@RequestParam(value="attach",required=false)MultipartFile attach) {
    	String idPicPath = "";  //用来保存上传文件的路径
   	 if (!attach.isEmpty()) {
   		 //1.的搭配保存上传文件路径，要将图片路径伤处拿到Tomact服务器中
			String uploadFilePath = request.getSession().getServletContext().getRealPath("statis"+File.separator+"uploadFile");
		    String fileName = attach.getOriginalFilename();//得到员文件名（上传文件的名称）
		    String suffix = FilenameUtils.getExtension(fileName);//去文件名的后缀
		    if (attach.getSize() > 5000000) {//判断上传文件尺寸是否大于500kb
				request.setAttribute("uploadFileError", "上传大小不得超过500kb");
				return "redirect:/devappinfo/deveAddApp";
			}else if(suffix.equalsIgnoreCase("jpg") 
					|| suffix.equalsIgnoreCase("jpeg") 
					|| suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("pneg")){
				//判断上传文件是否符合要求，判断后缀是否等于jpg,jpeg,png,pneg
				//保证文件名的唯一性
				String oldFileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
				File file = new File(uploadFilePath,oldFileName);
				if (!file.exists()) {
					file.mkdirs();//如果文件名不存在就创建
				}
				try {
					//将需要上传的文件上传
					attach.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*文件上传失败");
				}
				idPicPath = request.getContextPath() + file.separator+"static"+
				        File.separator + "uploadfiles"+File.separator + fileName;
			}		    
		}
   	 	appInfo.setModifyBy(((DevUser)request.getSession().getAttribute("DevUseruserSession")).getId());
   	 	appInfo.setModifyDate(new Date());
   	 	appInfo.setLogoPicPath(idPicPath);
   	 	appInfo.setLogoLocPath(idPicPath);
   	 	int result = devAppInfoService.devUpdateAppInfo(appInfo);
   	 	if (result > 0) {
			return "redirect:/devappinfo/develist";
		}else {
			return "redirect:/devappinfo/appinfomodify";
		}
    }
	
	//跳转到修改APP页面
	@RequestMapping(value="/devtomodifyAppInfo/{id}")
	public String devtomodifyAppInfo(@PathVariable("id")Integer id,Model model){
		AppInfo modifyAppInfo = devAppInfoService.devgetAppPageInfo(id);
		model.addAttribute("appInfo",modifyAppInfo);
		return "developer/appinfomodify";
	}
		
	
	
	
	//查看APP信息
	@RequestMapping(value="/devgetAppInfo/{id}")
	public String devgetAppInfo(@PathVariable("id")Integer id,Model model){
		AppInfo appinfoView = devAppInfoService.devgetAppPageInfo(id);
		model.addAttribute("appInfo",appinfoView);
		return "developer/appinfoview";
	}
	
	
	
	//删除APP
	 @RequestMapping(value="/devdeleteApp",method=RequestMethod.GET)
	 @ResponseBody
     public Object devdeleteApp(@RequestParam("id")Integer id,Model model) {
    	Map<String, String> result = new HashMap<>();
    	 if (devAppInfoService.devDeleteApp(id) > 0) {
    		 result.put("delResult","true");
		}else {
			result.put("delResult", "false");
		}
         return JSONArray.toJSONString(result);  //转换格式
     }
	
	 //新增APP
    @RequestMapping(value="/deveappinfoaddsave",method=RequestMethod.POST)
    public String devappinfoaddsave(AppInfo appInfo,HttpServletRequest request,HttpSession session,@RequestParam(value="a_logoPicPath",required=false)MultipartFile attach) {
    	String idPicPath = "";  //用来保存上传文件的路径
   	 if (!attach.isEmpty()) {
   		 //1.的搭配保存上传文件路径，要将图片路径伤处拿到Tomact服务器中
			String uploadFilePath = request.getSession().getServletContext().getRealPath("statis"+File.separator+"uploadFile");
		    String fileName = attach.getOriginalFilename();//得到员文件名（上传文件的名称）
		    String suffix = FilenameUtils.getExtension(fileName);//去文件名的后缀
		    if (attach.getSize() > 5000000) {//判断上传文件尺寸是否大于500kb
				request.setAttribute("uploadFileError", "上传大小不得超过500kb");
				return "redirect:/devappinfo/deveAddApp";
			}else if(suffix.equalsIgnoreCase("jpg") 
					|| suffix.equalsIgnoreCase("jpeg") 
					|| suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("pneg")){
				//判断上传文件是否符合要求，判断后缀是否等于jpg,jpeg,png,pneg
				//保证文件名的唯一性
				String oldFileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
				File file = new File(uploadFilePath,oldFileName);
				if (!file.exists()) {
					file.mkdirs();//如果文件名不存在就创建
				}
				try {
					//将需要上传的文件上传
					attach.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*文件上传失败");
				}
				idPicPath = request.getContextPath() + file.separator+"static"+
				        File.separator + "uploadfiles"+File.separator + fileName;
			}		    
		}
   	 	appInfo.setCreatedBy(((DevUser)request.getSession().getAttribute("DevUseruserSession")).getId());
   	 	appInfo.setCreationDate(new Date());
   	 	appInfo.setLogoPicPath(idPicPath);
   	 	int result = devAppInfoService.devAddApp(appInfo);
   	 	if (result > 0) {
			return "redirect:/devappinfo/develist";
		}else {
			return "redirect:/devappinfo/deveAddApp";
		}
    }
    
    
    //查询平台列表
    @RequestMapping(value="/devedatadictionarylist",method=RequestMethod.GET)
    @ResponseBody
	public Object devedatadictionarylist(@RequestParam("tcode")String tcode){
    	List<DataDictionary> flatFormList = devDataDictionaryService.queryByTypeCode(tcode);
    	return flatFormList ;
		
	}
	
	
	//跳转到新增APP页面
	@RequestMapping("/deveAddApp")
	public String deveAddApp(){
		return "developer/appinfoadd";
		
	}
	
		
	//分页并按照条件查询信息
		@RequestMapping("/develist")
		public String Develist(Model model,
				@RequestParam(value="querySoftwareName",required = false)String querySoftwareName,
		        @RequestParam(value="queryStatus",required=false)Integer queryStatus,
				@RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,
				@RequestParam(value="queryCategoryLevel1",required=false)Integer queryCategoryLevel1,
				@RequestParam(value="queryCategoryLevel2",required=false)Integer queryCategoryLevel2,
				@RequestParam(value="queryCategoryLevel3",required=false)Integer queryCategoryLevel3,
				@RequestParam(value="pageIndex",required=false)Integer currPageNo){
			//获取分页所需要的总记录数
			int totalCount = devAppInfoService.queryCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
			//每页显示5条
			int pageSize = 5;
			//当前页码
			currPageNo = currPageNo != null ? currPageNo : 1;
			//获取分页sql所需要的SQL对象
			int from = (currPageNo-1)*pageSize;
			Page page = new Page();
			page.setCurrPageNo(currPageNo);
			page.setPageSize(pageSize);
			page.setTotalCount(totalCount);
			
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
			//model.addAttribute("categoryLevel2List", categoryLevel2List);
			//model.addAttribute("categoryLevel3List", categoryLevel3List);
			model.addAttribute("pages",page );
			//将查询条件
			model.addAttribute("querySoftwareName", querySoftwareName);
			model.addAttribute("queryStatus",queryStatus);
			model.addAttribute("queryFlatformId",queryFlatformId );
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1 );
			model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
			model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
			
			return "developer/appinfolist";  //跳转到APP品牌页面
		}
		
		
		//根据parentId到app_info1category表去查询相关的分类信息
		@RequestMapping(value="/queryCategoryListByParentId",method = RequestMethod.GET)
		@ResponseBody
		public Object queryCategoryListByParentId(@RequestParam("pid")Integer pid){
			List<AppCategory> appCategoryList = devAppCategoryService.queryById(pid);
			return appCategoryList;
		}
	
}
