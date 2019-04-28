package com.mt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mt.pojo.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
	
	//根据订单号获取订单详情
	@Query("select o from com.mt.pojo.OrderItem o where o.orderNo=:orderNo")
	List<OrderItem> orderListByOrderNo(@Param("orderNo")Long orderNo);
}
