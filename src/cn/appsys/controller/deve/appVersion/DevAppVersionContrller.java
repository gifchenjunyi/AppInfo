package cn.appsys.controller.deve.appVersion;

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

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.devappinfo.DevAppInfoService;
import cn.appsys.service.deve.devappversion.DevVersionService;

@Controller
@RequestMapping("/devappVersion")
public class DevAppVersionContrller {
	
	@Resource(name="devVersionService")
	private DevVersionService devVersionService;
	
	@Resource(name="devAppInfoService")
	private DevAppInfoService devAppInfoService;
	
	
	//修改保存
		@RequestMapping("/appVersionModifySave")
		public String appVersionModifySave(AppVersion appVersion,HttpSession session,
				@RequestParam("attach")MultipartFile attach,HttpServletRequest request) {
			String downloadLink =  null;
			String apkLocPath = null;
			String apkFileName = null;
			if(!attach.isEmpty()){
				String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
				String oldFileName = attach.getOriginalFilename();//原文件名
				String suffix = FilenameUtils.getExtension(oldFileName);//原文件后缀
				if(suffix.equalsIgnoreCase("apk")){
					 AppInfo appInfo = devAppInfoService.queryInfoByIdAndAPK(appVersion.getAppId(),null);
					 String apkName = appInfo.getAPKName();
					 apkFileName = apkName + "-" + appInfo.getVersionNo() + ".apk";//apk文件命名：apk名称+版本号+.apk
					 File targetFile = new File(path,apkFileName);
					 if(!targetFile.exists()){
						 targetFile.mkdirs();
					 }
					 try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						session.setAttribute("fileUploadError", "* apk文件上传失败！");
					} 
					downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
					apkLocPath = path+File.separator+apkFileName;
				}else{
					session.setAttribute("fileUploadError", "* 上传文件的格式不正确，请选择正确的apk格式的文件！");
				}
			}
			appVersion.setModifyBy(((DevUser)session.getAttribute("DevUseruserSession")).getId());
			appVersion.setModifyDate(new Date());
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setApkFileName(apkFileName);
			int result = devVersionService.modifyAppVersion(appVersion);
			if (result > 0) {
				return "redirect:/devappinfo/develist";
			}else {
				return "redirect:/devappVersion/todevAppVersionModify";
			}

		}

	
	
	/*
	 * 修改版本信息页面的删除
	 */
	@RequestMapping(value = "/delapkfile", method = RequestMethod.GET)
	@ResponseBody
	public Object delApkFile(@RequestParam("id")Integer id) {
		Map<String, String> map = new HashMap<String,String>();
		if (id == null) {
			map.put("result", "failed");
		} else {
			//通过版本id查询指定的app版本详情信息
			AppVersion appVersion = devVersionService.getAppVersionByVid(id);
			String apkLocPath = appVersion.getApkLocPath(); //得到apk文件的绝对路径
			/*利用apk文件的绝对路径创建File对象*/
			File file = new File(apkLocPath);
			if (file.exists()) {
				if (file.delete()) {//如果file对象存在，就把该目录下的文件删除。即将服务器存储的物理文件删除
					if(devVersionService.deleteApkFile(id)) {
						map.put("result", "success");
					}
				}
			} else {
				map.put("result", "noexist"); //表示服务器指定目录中不存在该物理文件
			}
		}
		return map;
	}

	
	
	//跳转到修改版本信息
	@RequestMapping("/todevAppVersionModify")
	public String todevAppVersionModify(@RequestParam("vid")Integer vid,
				                            @RequestParam("aid")Integer aid,Model model){
		AppVersion appVersion = devVersionService.getAppVersionByVid(vid);
		List<AppVersion> appVersionslist = devVersionService.getAppVersionlist(aid);
		model.addAttribute("appVersion",appVersion);
		model.addAttribute("appVersionslist",appVersionslist);
		return "developer/appversionmodify";
	}
	
	
	 //新增版本信息
    @RequestMapping(value="/devaddversionsave",method=RequestMethod.POST)
    public String devappinfoaddsave(AppVersion appVersion,HttpServletRequest request,HttpSession session,@RequestParam(value="a_downloadLink",required=false)MultipartFile attach) {
    	String idPicPath = "";  //用来保存上传文件的路径
    	String oldFileName = "";
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
				oldFileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
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
   	 	appVersion.setCreatedBy(((DevUser)request.getSession().getAttribute("DevUseruserSession")).getId());
   	 	appVersion.setCreationDate(new Date());
   	 	appVersion.setModifyBy(((DevUser)request.getSession().getAttribute("DevUseruserSession")).getId());
   	 	appVersion.setModifyDate(new Date());
   	 	appVersion.setApkLocPath(idPicPath);
   	 	appVersion.setApkFileName(oldFileName);
   	 	int result = devVersionService.devAppVersion(appVersion);
   	 	if (result > 0) {
			return "redirect:/devappinfo/develist";
		}else {
			return "redirect:/devappVersion/todevAppVersionAdd";
		}
    }
	
	
	
	
	
	//新增版本信息
	@RequestMapping("/todevAppVersionAdd/{id}")
	public String todevAppVersionAdd(@PathVariable("id")Integer id,Model model){
		List<AppVersion> appVersionslist = devVersionService.getAppVersionlist(id);
		model.addAttribute("id",id);
		model.addAttribute("appVersionList",appVersionslist);
		return "developer/appversionadd";
	}
	
    
}
