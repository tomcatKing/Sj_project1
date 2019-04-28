package com.mt.service;


import org.springframework.data.domain.Pageable;

import com.mt.pojo.Food;
import com.mt.resp.JsonResult;

public interface IFoodService {
	//添加,修改
	JsonResult SaveOrUpdate(Food food);
	
	//查询所有
	JsonResult getFoodList(Pageable pageable);
	
	//查询一个
	JsonResult getByFoodId(Integer foodId);
	
}
