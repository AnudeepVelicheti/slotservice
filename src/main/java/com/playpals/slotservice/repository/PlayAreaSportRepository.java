package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.PlayAreaSport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayAreaSportRepository extends JpaRepository<PlayAreaSport,Integer> {

    void deleteByPlayAreaId(Integer playAreaId);

    boolean existsByPlayAreaIdAndSportId(Integer playAreaId, Integer sportId);

    List<PlayAreaSport> findByPlayAreaId(Integer playAreaId);
}


