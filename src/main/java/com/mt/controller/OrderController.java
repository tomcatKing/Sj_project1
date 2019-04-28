package com.mt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mt.pojo.Order;
import com.mt.resp.JsonResult;
import com.mt.service.IOrderService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("boss/order")
@Log4j
public class OrderController {
	@Autowired
	private IOrderService iOrderService;
	
	@RequestMapping("/list")
	public JsonResult orderList(
			@RequestParam(required=false,defaultValue="1",name="pageNum") int pageNum,
			@RequestParam(required=false,defaultValue="10",name="pageSize") int pageSize) {
		log.info("获取订单列表服务启动了");
		return iOrderService.getOrderList(pageNum,pageSize);
	}
	
	@GetMapping("/{orderNo}")
	public JsonResult detail(@PathVariable(name="orderNo")Long orderNo) {
		log.info("获取订单详情服务启动了");
		return iOrderService.orderDetail(orderNo);
	}
	
	@PostMapping("/update")
	public JsonResult update(Order order) {
		log.info("修改订单列表服务启动了");
		return iOrderService.updateOrder(order);
	} 
	
}
