package work.metanet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.base.BaseController;

@Controller
@RequestMapping("main")
public class MainController extends BaseController{
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping
	public String main(HttpServletRequest request) {
		request.setAttribute("admin", getAdmin());
		return "page/main/main";
	}
	
}
