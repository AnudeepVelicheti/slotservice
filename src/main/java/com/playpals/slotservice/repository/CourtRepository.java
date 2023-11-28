package com.playpals.slotservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.playpals.slotservice.model.Courts;


@Repository
public interface CourtRepository extends  JpaRepository<Courts, Integer>{

//	@Query(value = "Select s from Courts s where playAreaId=:playAreaId and sportId=:sportId")
//	Optional<List<Courts>> findCourtsByTime(int playAreaId,int sportId);
@Query("SELECT c FROM Courts c WHERE c.playAreaId = :playAreaId AND c.sportId = :sportId")
Optional<List<Courts>> findCourtsByTime(int playAreaId, int sportId);


    void deleteByPlayAreaId(Integer newPlayAreaId);

    boolean existsByPlayAreaIdAndSportIdAndName(Integer newPlayAreaId, int id, String courtName);
}
