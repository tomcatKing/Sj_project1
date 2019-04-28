package com.mt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mt.common.Const;
import com.mt.dao.OrderItemRepository;
import com.mt.dao.RoomRepository;
import com.mt.pojo.Order;
import com.mt.pojo.OrderItem;
import com.mt.pojo.Room;
import com.mt.resp.JsonResult;
import com.mt.service.IRoomService;
import com.mt.vo.RoomVo;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class RoomServiceImpl implements IRoomService {
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	@Transactional(readOnly=true)
	public JsonResult getRoomList(Pageable pageable) {
		Page<Room> roomList=roomRepository.roomList(pageable);
		int currentPage=roomList.getNumber();
		int totalPage=roomList.getTotalPages();
		int pageRows=pageable.getPageSize();
		List<RoomVo> roomVoList=new ArrayList<RoomVo>();
		for (Room room : roomList) {
			roomVoList.add(RoomVo.accessRoomVo(room));
		}
		com.mt.resp.Page result=new com.mt.resp.Page();
		result.setCurrentPage(currentPage);
		result.setPageRows(pageRows);
		com.mt.resp.Page.initPage(result, roomVoList, totalPage);
		return JsonResult.ok(result);
	}

	@Override
	@Transactional(readOnly=true)
	public JsonResult getRoomById(Integer roomId) {
		Room room=roomRepository.roomDetail(roomId);
		if(room==null) {
			return JsonResult.errorMsg("房间不存在!!");
		}
		Set<Order> orderList=room.getOrderList();
		
		List<OrderItem> finalOrderItemList=new ArrayList<OrderItem>();
		for (Order order : orderList) {
			//如果这个订单已经付款
			if(order.getStatus()==Const.OrderStatusEnum.ISPAY.getCode()) {
				List<OrderItem> orderItemList=orderItemRepository.orderListByOrderNo(order.getOrderNo());
				for(OrderItem orderItem:orderItemList) {
					finalOrderItemList.add(orderItem);
				}
			}
		}
		RoomVo roomVo=RoomVo.transRoomVo(room, finalOrderItemList);
		return JsonResult.ok(roomVo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public JsonResult saveOrUpdate(Room room) {
		log.info("room的值为"+room);
		if(room==null) {
			return JsonResult.errorMsg("传入参数错误!!");
		}
		Date now=new Date();
		Room newroom=null;
		if(room.getRoomId()!=null) {
			//更新操作
			newroom=roomRepository.findById(room.getRoomId()).get();
			if(newroom==null) {
				return JsonResult.errorMsg("这个房间不存在!!");
			}
			if(room.getRoomName()!=null) {
				newroom.setRoomName(room.getRoomName());
			}
			if(room.getRoomAddress()!=null) {
				newroom.setRoomAddress(room.getRoomAddress());
			}
			Integer room_type=room.getRoomType();
			if(room_type!=null && (room_type==Const.RoomTypeEnum.BASIC.getCode() || room_type!=Const.RoomTypeEnum.VIP.getCode())) {
				newroom.setRoomType(room_type);
			}
			BigDecimal room_price=room.getRoomPrice();
			if(room_price!=null && room_price.doubleValue()>0) {
				newroom.setRoomPrice(room_price);
			}
			Integer room_status=room.getRoomStatus();
			if(room_status!=null && room_status==Const.RoomStatusEnum.KESHIYON.getCode() || 
					room_status==Const.RoomStatusEnum.YISHIYON.getCode() || room_status==Const.RoomStatusEnum.NOSHIYON.getCode()) {
				newroom.setRoomStatus(room_status);
			}
			newroom.setUpdateTime(now);
		}else {
			//添加操作
			newroom=new Room();
			if(room.getRoomName()!=null) {
				newroom.setRoomName(room.getRoomName());
			}else {
				newroom.setRoomName("Ax_x0x");
			}
			
			if(room.getRoomAddress()!=null) {
				newroom.setRoomAddress(room.getRoomAddress());
			}else {
				newroom.setRoomAddress("晋西北平安县醉仙楼A区x栋x0x房");
			}
			BigDecimal room_price=room.getRoomPrice();
			if(room_price!=null) {
				newroom.setRoomPrice(room_price);
			}else {
				newroom.setRoomPrice(new BigDecimal("50"));
			}
			
			Integer room_type=room.getRoomType();
			if(room_type!=null || room_type==Const.RoomTypeEnum.BASIC.getCode() || room_type!=Const.RoomTypeEnum.VIP.getCode()) {
				newroom.setRoomType(room_type);
			}else {
				if(newroom.getRoomPrice().doubleValue()>50) {
					newroom.setRoomType(Const.RoomTypeEnum.VIP.getCode());
				}else {}
					newroom.setRoomType(Const.RoomTypeEnum.BASIC.getCode());
			}
			
			Integer room_status=room.getRoomStatus();
			if(room_status!=null || room_status==Const.RoomStatusEnum.KESHIYON.getCode() || 
					room_status==Const.RoomStatusEnum.YISHIYON.getCode() || room_status==Const.RoomStatusEnum.NOSHIYON.getCode())
			{
				newroom.setRoomStatus(room_status);
			}else {
				newroom.setRoomStatus(Const.RoomStatusEnum.KESHIYON.getCode());
			}
			newroom.setCreatetTime(now);
			newroom.setUpdateTime(now);
		}
		
		Room returnRoom=roomRepository.save(newroom);
		RoomVo roomVo=RoomVo.accessRoomVo(returnRoom);
		return JsonResult.ok(roomVo);
	}


}
