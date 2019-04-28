package com.mt.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mt.common.Const;
import com.mt.pojo.Order;
import com.mt.util.DateUtil;

import lombok.Data;

@Data
@JsonInclude(value=Include.NON_NULL)
public class OrderVo implements Serializable{
	private Integer orderId;
	private Long orderNo;
	private List<OrderItemVo> orderItemVoList;
	private UserVo userVo;
	private RoomVo roomVo;
	private BigDecimal totalPrice;
	private String paymentType;
	private String status;
	private String orderDesc;
	private String createTime;
	
	public static OrderVo accessOrderVo(Order order,
			List<OrderItemVo> orderItemVoList,RoomVo roomVo) {
		OrderVo orderVo=new OrderVo();
		orderVo.setOrderId(order.getOrderId());
		orderVo.setOrderNo(order.getOrderNo());
		orderVo.setOrderItemVoList(orderItemVoList);
		orderVo.setRoomVo(roomVo);
		orderVo.setTotalPrice(order.getPayment());
		orderVo.setPaymentType(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getVal());
		orderVo.setStatus(Const.OrderStatusEnum.codeOf(order.getStatus()).getVal());
		orderVo.setOrderDesc(order.getOrderDesc());
		orderVo.setCreateTime(DateUtil.dateToString(order.getCreateTime()));
		return orderVo;
	}
	
	public static OrderVo smallOrderVo(Order order) {
		OrderVo orderVo=new OrderVo();
		orderVo.setOrderId(order.getOrderId());
		orderVo.setOrderNo(order.getOrderNo());
		orderVo.setUserVo(UserVo.accessUserVo(order.getUserId()));
		orderVo.setTotalPrice(order.getPayment());
		orderVo.setStatus(Const.OrderStatusEnum.codeOf(order.getStatus()).getVal());
		return orderVo;
	}
}
