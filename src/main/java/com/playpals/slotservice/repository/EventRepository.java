package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {





    @Transactional
    @Modifying
    @Query("DELETE FROM Event e WHERE e.courtId = ?1")
    void deleteByCourtId(Integer courtId);


    List<Event> findByPlayAreaId(Integer playAreaId);
    void deleteByPlayAreaId(Integer playAreaId);
}
