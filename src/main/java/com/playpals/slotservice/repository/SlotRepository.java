package com.playpals.slotservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpals.slotservice.model.Slot;

@Repository
public interface SlotRepository extends  JpaRepository<Slot, Integer>{
	
	@Query(value = "Select s.* from slots s join play_area_timings p on (s.) where ")
	Optional<List<Slot>> findSlotsByTime(String startTime,String endTime);

}
