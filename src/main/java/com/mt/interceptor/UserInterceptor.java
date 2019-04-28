package com.mt.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mt.common.Const;
import com.mt.resp.JsonResult;

@Component
public class UserInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("拦截器开始工作!!");
		HttpSession session=request.getSession();
		System.out.println(session.getAttribute(Const.CURRENT_USER));
		if(session.getAttribute(Const.CURRENT_USER)==null) {
			System.out.println("请求不放行");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out=response.getWriter();
			JsonResult jsonResult=JsonResult.errorMsg("当前用户未登录");
			out.print(jsonResult);
			return false;
		}else {
			System.out.println("请求放行,用户请求["+request.getServletPath()+","+request.getQueryString()+"]");
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
}
