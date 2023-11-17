package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.PlayArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayAreaRepository extends JpaRepository<PlayArea,Integer> {
}
