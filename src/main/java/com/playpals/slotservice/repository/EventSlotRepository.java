package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.EventSlots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventSlotRepository extends JpaRepository<EventSlots,Integer> {

    void deleteByPlayAreaId(Integer playAreaId);
}
