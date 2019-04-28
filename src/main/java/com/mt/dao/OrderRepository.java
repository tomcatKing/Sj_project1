package com.mt.dao;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mt.pojo.Order;
import com.mt.pojo.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value="select new com.mt.pojo.Order(o.orderId,o.orderNo,o.userId,o.payment,o.status) from com.mt.pojo.Order o")
	List<Order> orderList(); 
	
	@Query(value="select o from com.mt.pojo.Order o  where o.orderNo=:orderNo")
	Order orderDetail(@Param("orderNo") Long orderNo);
	
	
}
