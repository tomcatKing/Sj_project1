package com.mt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.pojo.Food;
import com.mt.resp.JsonResult;
import com.mt.service.IFoodService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("boss/food")
@Log4j
public class FoodController {
	@Autowired
	private IFoodService iFoodService;
	
	@RequestMapping("/list")
	public JsonResult list(@PageableDefault(
			direction=Direction.DESC,
			page=0,
			size=10)
			Pageable pageable) {
		log.info("获取美食列表启动了");
		return iFoodService.getFoodList(pageable);
	}
	
	@GetMapping("/{foodId}")
	public JsonResult get(@PathVariable(name="foodId")Integer foodId) {
		log.info("获取美食详情启动了");
		return iFoodService.getByFoodId(foodId);
	}
	
	@PostMapping("/su")
	public JsonResult saveOrUpdate(Food food) {
		log.info("修改美食启动了");
		return iFoodService.SaveOrUpdate(food);
	}
}
