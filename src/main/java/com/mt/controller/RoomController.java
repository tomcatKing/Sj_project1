package com.mt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mt.pojo.Room;
import com.mt.resp.JsonResult;
import com.mt.service.IRoomService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("boss/room")
@Log4j
public class RoomController {
	@Autowired
	private IRoomService iRoomService;
	
	@RequestMapping("/list")
	public JsonResult list(@PageableDefault(page=0,size=10)Pageable pageable) {
		log.info("获取所有房间的信息启动了");
		return iRoomService.getRoomList(pageable);
	}
	
	@GetMapping("/{roomId}")
	public JsonResult roomDetail(@PathVariable(name="roomId") Integer roomId) {
		log.info("获取房间的详细信息启动了");
		return iRoomService.getRoomById(roomId);
	}
	
	@PostMapping("/su")
	public JsonResult saveOrUpdate(Room room) {
		log.info("修改房间的信息启动了");
		return iRoomService.saveOrUpdate(room);
	}

	
}
