package cn.appsys.controller.back.backendUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/backenduser")
public class backendUserController {

	    //跳转到后台登录页面
		@RequestMapping("/tobackendLoginUser")
		public String tobackendLoginUser(){
			return "backendlogin";
		}
}
