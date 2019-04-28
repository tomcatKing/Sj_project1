package com.mt.service;

import org.springframework.data.domain.Pageable;

import com.mt.pojo.Room;
import com.mt.resp.JsonResult;

public interface IRoomService{
	JsonResult getRoomList(Pageable pageable);
	
	JsonResult getRoomById(Integer roomId);
	
	JsonResult saveOrUpdate(Room room);
}
