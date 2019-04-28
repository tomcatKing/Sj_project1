package com.mt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mt.common.Const;
import com.mt.dao.OrderItemRepository;
import com.mt.dao.OrderRepository;
import com.mt.pojo.Order;
import com.mt.pojo.OrderItem;
import com.mt.pojo.Room;
import com.mt.resp.JsonResult;
import com.mt.resp.Page;
import com.mt.service.IOrderService;
import com.mt.vo.OrderItemVo;
import com.mt.vo.OrderVo;
import com.mt.vo.RoomVo;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	@Transactional(readOnly=true)
	public JsonResult getOrderList(int currentPage,int pageSize) {
		Long count=orderRepository.count();
		Page p=new Page();
		p.setCurrentPage(currentPage);
		p.setPageRows(pageSize);
		List<Order> orderList=orderRepository.orderList();
		List<OrderVo> orderVoList=new ArrayList<>();
		for (Order order : orderList) {
			orderVoList.add(OrderVo.smallOrderVo(order));
		}
		p=Page.initPage(p, orderVoList, count.intValue());
		return JsonResult.ok(p);
	}

	@Override
	@Transactional
	public JsonResult orderDetail(Long orderNo) {
		Order order=orderRepository.orderDetail(orderNo);
		if(order==null) {
			return JsonResult.errorMsg("该订单不存在!!");
		}
		List<OrderItem> orderItemList=orderItemRepository.orderListByOrderNo(orderNo);
		Room room=order.getRoomId();
		List<OrderItemVo> orderItemVoList=new ArrayList<>();
		for(OrderItem orderItem:orderItemList) {
			orderItemVoList.add(OrderItemVo.accessOrderItemVo(orderItem));
		}
		OrderVo orderVo=OrderVo.accessOrderVo(order, orderItemVoList, RoomVo.accessRoomVo(room));
		return JsonResult.ok(orderVo);
	}

	@Override
	public JsonResult updateOrder(Order order) {
		log.info("传入参数,order"+order);
		if(order==null || order.getOrderId()==null || order.getOrderNo()==null || order.getStatus()==null) {
			return JsonResult.errorMsg("传入参数错误!!");
		}
		Order oldOrder=orderRepository.findById(order.getOrderId()).get();
		if(oldOrder==null) {
			return JsonResult.errorMsg("订单不存在!!");
		}
		if(order.getStatus()==30) {
			oldOrder.setStatus(Const.OrderStatusEnum.SUCCESS.getCode());
		}
		
		Order returnOrder= orderRepository.save(oldOrder);
		if(returnOrder==null) {
			return JsonResult.errorMsg("数据未更新!!");
		}
		OrderVo orderVo=OrderVo.smallOrderVo(returnOrder);
		return JsonResult.ok(orderVo);
	}

}
