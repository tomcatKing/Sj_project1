package com.mt.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mt.common.Const;
import com.mt.dao.FoodRepository;
import com.mt.pojo.Food;
import com.mt.resp.JsonResult;
import com.mt.service.IFoodService;
import com.mt.vo.FoodVo;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class FoodServiceImpl implements IFoodService{
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult SaveOrUpdate(Food food) {
		log.info("传入参数food->"+food);
		if(food==null || food.getFoodType()==null ||
				food.getFoodName()==null || food.getFoodImg()==null) {
			return JsonResult.errorMsg("传入参数错误");
		}
		Date now=new Date();
		Food newFood=null;
		if(food.getFoodId()!=null) {
			//这里是更新操作
			newFood=foodRepository.findById(food.getFoodId()).get();
			Integer food_Type=food.getFoodType();
			if(food_Type!=null && (food_Type==Const.FoodTypeEnum.GAOTAN.getCode() || food_Type==Const.FoodTypeEnum.HUNCAI.getCode()
					|| food_Type==Const.FoodTypeEnum.LINNIAO.getCode() || food_Type==Const.FoodTypeEnum.SUCAI.getCode() || 
					food_Type==Const.FoodTypeEnum.TIANDIAN.getCode() || food_Type==Const.FoodTypeEnum.XIAOCHI.getCode())) {
				newFood.setFoodType(food.getFoodType());
			}
			if(food.getFoodName()!=null) {
				newFood.setFoodName(food.getFoodName());
			}
			if(food.getFoodImg()!=null) {
				newFood.setFoodImg(food.getFoodImg());
			}
			if(food.getFoodDetail()!=null) {
				newFood.setFoodDetail(food.getFoodDetail());
			}
			if(food.getFoodPrice()!=null || food.getFoodPrice().doubleValue()>0) {
				newFood.setFoodPrice(food.getFoodPrice());
			}
			Integer food_status=food.getFoodStatus();
			if(food_status!=null &&(food_status==Const.FoodStatusEnum.SHANCHU.getCode() || food_status==Const.FoodStatusEnum.XIAJIA.getCode() 
					|| food_status==Const.FoodStatusEnum.ZAISHOU.getCode())) {
				newFood.setFoodStatus(food_status);
			}
			newFood.setUpdateTime(now);
		}else {
			//这里是添加操作
			newFood=new Food();
			//判断值
			Integer food_Type=food.getFoodType();
			if(food_Type!=null && (food_Type==Const.FoodTypeEnum.GAOTAN.getCode() || food_Type==Const.FoodTypeEnum.HUNCAI.getCode()
					|| food_Type==Const.FoodTypeEnum.LINNIAO.getCode() || food_Type==Const.FoodTypeEnum.SUCAI.getCode() || 
					food_Type==Const.FoodTypeEnum.TIANDIAN.getCode() || food_Type==Const.FoodTypeEnum.XIAOCHI.getCode())) {
				newFood.setFoodType(food.getFoodType());
			}
			if(food.getFoodName()!=null) {
				newFood.setFoodName(food.getFoodName());
			}else {
				newFood.setFoodName("无名美食");
			}
			if(food.getFoodImg()!=null) {
				newFood.setFoodImg(food.getFoodImg());
			}else {
				newFood.setFoodImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556963025&di=110c8361644b6098ab5404ceb1f56435&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.tukexw.com%2Fimg%2Fe40fb8b53441be9b.jpg");
			}
			if(food.getFoodDetail()!=null) {
				newFood.setFoodDetail(food.getFoodDetail());
			}
			if(food.getFoodPrice()!=null || food.getFoodPrice().doubleValue()>0) {
				newFood.setFoodPrice(food.getFoodPrice());
			}
			Integer food_status=food.getFoodStatus();
			if(food_status!=null &&(food_status==Const.FoodStatusEnum.SHANCHU.getCode() || food_status==Const.FoodStatusEnum.XIAJIA.getCode() 
					|| food_status==Const.FoodStatusEnum.ZAISHOU.getCode())) {
				newFood.setFoodStatus(food_status);
			}
			newFood.setCreateTime(now);
			newFood.setUpdateTime(now);	
		}
		
		Food returnFood=foodRepository.save(newFood);
		if(returnFood==null) {
			return JsonResult.errorMsg("更新失败!!");
		}else {
			FoodVo foodVo=FoodVo.accessFoodVo(returnFood);
			return JsonResult.ok(foodVo);
		}
		
	
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult getFoodList(Pageable pageable) {
		Page<Food> foodList=foodRepository.foodList(pageable);
		return JsonResult.ok(foodList);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult getByFoodId(Integer foodId) {
		Food food=foodRepository.foodDetail(foodId);
		if(food==null) {
			return JsonResult.errorMsg("美食不存在!!");
		}
		return JsonResult.ok(FoodVo.transFoodVo(food));
	}


}
