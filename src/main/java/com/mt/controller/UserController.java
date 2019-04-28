package com.mt.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.common.Const;
import com.mt.pojo.Admin;
import com.mt.resp.JsonResult;
import com.mt.service.IUserService;
import com.mt.util.PropertiesUtil;
import com.mt.util.TokenCache;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/user")
@Log4j
public class UserController {
	@Autowired
	private IUserService iUserService;
	
	//1.获取验证码
	@GetMapping("/code")
	public JsonResult code() {
		log.info("获取验证码被调用");
		return iUserService.getcode();
	}
	
	//2.登录
	@PostMapping("/login")
	public JsonResult login(HttpSession session,String userName,String code) {
		log.info("登录被调用,userName->"+userName+",code->"+code);
		if(userName==null || code==null || userName.trim()=="" || code.trim()=="") {
			return JsonResult.errorMsg("参数错误");
		}
		String email=PropertiesUtil.getProperty("email.emailname", "15113437287@163.com");
		String code_val=TokenCache.getKey(email);
		if(code_val==null) {
			return JsonResult.errorMsg("验证码已过期!!");
		}
		if(!code_val.equals(code)) {
			return JsonResult.errorMsg("验证不通过!!");
		}
		if(userName.equals("admin") && code.equals(code_val)) {
			Admin admin=new Admin();
			admin.setUserName(userName);
			session.setAttribute(Const.CURRENT_USER, admin);
			return JsonResult.ok();
		}else {
			return JsonResult.errorMsg("账号不匹配");
		}
	}
	
	//3.退出
	@GetMapping("/logout")
	public JsonResult exit(HttpSession session) {
		log.info("退出被调用");
		Admin admin=(Admin) session.getAttribute(Const.CURRENT_USER);
		if(admin==null) {
			return JsonResult.errorMsg("当前用户未登录");
		}else {
			session.removeAttribute(Const.CURRENT_USER);
			return JsonResult.ok();
		}
	}
	
	//获取当前登录的用户信息接口
	@GetMapping("/info")
	public JsonResult info(HttpSession session) {
		log.info("获取当前登录的用户信息被调用");
		Admin admin=(Admin) session.getAttribute(Const.CURRENT_USER);
		if(admin==null) {
			return JsonResult.errorMsg("当前用户未登录");
		}else {
			return JsonResult.ok(admin);
		}
	}
}
