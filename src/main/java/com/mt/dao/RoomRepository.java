package com.mt.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mt.pojo.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	@Query(value="select new com.mt.pojo.Room(roomId,roomName,roomAddress,roomPrice,roomType,roomStatus) from com.mt.pojo.Room",
			countQuery="select count(1) from com.mt.pojo.Room")
	Page<Room> roomList(Pageable pageable);
	
	@Query("select r from com.mt.pojo.Room r where r.roomId=:roomId")
	Room roomDetail(@Param("roomId")Integer roomId);
}
