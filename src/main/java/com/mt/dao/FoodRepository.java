package com.mt.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mt.pojo.Food;

public interface FoodRepository extends JpaRepository<Food,Integer> {
	@Query(value="select new com.mt.pojo.Food(foodId,foodName,foodImg,foodPrice) from com.mt.pojo.Food",
			countQuery="select count(1) from com.mt.pojo.Food")
	Page<Food> foodList(Pageable pageable);

	@Query(value="select new com.mt.pojo.Food(foodId,foodType,foodName,foodImg,foodDetail,foodPrice,foodStatus,createTime,updateTime) from com.mt.pojo.Food where foodId=:foodId")
	Food foodDetail(@Param("foodId")Integer foodId);
}
