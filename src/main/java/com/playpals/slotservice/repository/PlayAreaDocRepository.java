package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.PlayAreaDoc;
import com.playpals.slotservice.model.PlayAreaTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayAreaDocRepository extends JpaRepository<PlayAreaDoc,Integer> {
}

