package com.mt.interceptor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mt.resp.JsonResult;

@RestControllerAdvice
public class GlobalException {
	/**
	 * 处理所有不可知异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({Exception.class})
	public JsonResult globalExcetpionHandler() {
		return JsonResult.errorMsg("未知的错误!!");
	}
	
	/**
	 * 处理所有业务异常
	 */
	@ExceptionHandler({RuntimeException.class})
	public JsonResult BusinessExceptionHandler() {
		return JsonResult.errorMsg("请求出错了!!");
	}
}