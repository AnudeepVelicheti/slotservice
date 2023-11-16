package com.playpals.slotservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpals.slotservice.model.PlayAreaTiming;


@Repository
public interface PlayAreaTimingRepository extends JpaRepository<PlayAreaTiming, Integer>{

	@Query(value="Select p from PlayAreaTiming p where day=:day and playAreaId=:playAreaId")
	Optional<PlayAreaTiming> getSlotsByDayAndPlayArea(String day,int playAreaId);
}
