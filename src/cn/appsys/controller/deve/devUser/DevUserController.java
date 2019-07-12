package cn.appsys.controller.deve.devUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.deve.devUser.DevUserService;


@Controller
@RequestMapping("/devuser")
public class DevUserController {
	
	@Resource(name="devUserService")
	private DevUserService devUserService;
	
	@RequestMapping("/Exit")
	public String Exit(HttpSession session) {
		//通过invalidate（）进行注释
	     session.invalidate();
		return "redirect:/index.jsp";
	
		
	}
	
	//负责处理前台登录
    @RequestMapping(value="/dodveLogin",method=RequestMethod.POST)
    public String doLogin(@RequestParam("devCode")String devCode,@RequestParam("devPassword")String devPassword,HttpServletRequest request) {
   	 DevUser devUser = devUserService.DevLogin(devCode, devPassword);
		if (devUser == null) {
			throw new RuntimeException("用户不存在!");
		}else if (!devPassword.equals(devUser.getDevPassword())) {
			throw new RuntimeException("密码错误!");
		}else {
			request.getSession().setAttribute("DevUseruserSession",devUser );
			return "redirect:/devuser/main";  //重定向到todevelogin
		}
	}

    //跳转到前台登录页面
  	@RequestMapping("/main")
  	public String main(){
  		return "developer/main";
  	}
    
    //跳转到前台登录页面
  	@RequestMapping("/todevelogin")
  	public String todevelogin(){
  		return "devlogin";
  	}
	
      
	//跳转到index主页面
	@RequestMapping("/")
	public String toIndex(){
		return "../index";
	}
	
	
}
