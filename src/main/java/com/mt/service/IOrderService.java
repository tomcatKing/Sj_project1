package com.mt.service;

import com.mt.pojo.Order;
import com.mt.resp.JsonResult;

public interface IOrderService {
	JsonResult getOrderList(int currentPage,int pageSize);
	
	JsonResult orderDetail(Long OrderNo);
	
	JsonResult updateOrder(Order order);
}
