package com.mt.service.impl;

import org.springframework.stereotype.Service;

import com.mt.resp.JsonResult;
import com.mt.service.IUserService;
import com.mt.util.MailUtil;
import com.mt.util.PropertiesUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Override
	public JsonResult getcode() {
		//这里固定使用15113437287@163.com
		String email=PropertiesUtil.getProperty("email.emailname", "15113437287@163.com");
		String result=MailUtil.send_mail(email);
		if(result!=null && result!="") {
			return JsonResult.ok();
		}
		return JsonResult.errorMsg("验证码发送失败!!");
	}

}
