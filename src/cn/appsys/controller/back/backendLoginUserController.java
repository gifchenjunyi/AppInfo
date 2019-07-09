package cn.appsys.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/backendloginuser")
public class backendLoginUserController {

	    //跳转到后台登录页面
		@RequestMapping("/tobackendLoginUser")
		public String tobackendLoginUser(){
			return "backendlogin";
		}
}
