package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.PlayAreaSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayAreaSportRepository extends JpaRepository<PlayAreaSport,Integer> {
}


