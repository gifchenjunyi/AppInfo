package cn.appsys.controller.deve;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/devuser")
public class DevUserController {
      
	//跳转到index主页面
	@RequestMapping("/toIndex")
	public String toIndex(){
		return "../index";
	}
	
	
	//跳转到前台登录页面
	@RequestMapping("/todevelogin")
	public String todevelogin(){
		return "devlogin";
	}
}
