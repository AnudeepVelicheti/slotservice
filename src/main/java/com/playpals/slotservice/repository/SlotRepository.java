package com.playpals.slotservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpals.slotservice.model.Slot;

@Repository
public interface SlotRepository extends  JpaRepository<Slot, Integer>{
	
	@Query(value = "Select s.* from slots s where s.id not in( Select e.slot_id from event_slots e where e.court_id=:courtId and e.play_area_id=:playAreaId and e.date=:date) and s.start_time>=:startTime and s.end_time<=:endTime ",nativeQuery = true)
	List<Slot> findSlotsByTime(int courtId,int playAreaId,int startTime,int endTime,String date);
	
	
}
