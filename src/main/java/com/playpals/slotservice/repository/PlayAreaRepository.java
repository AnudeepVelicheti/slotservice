package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.PlayArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayAreaRepository extends JpaRepository<PlayArea,Integer> {

    List<PlayArea> findByStatus(String status);


    List<PlayArea> findByName(String name);

    List<PlayArea> findByOwner(Integer ownerId);


    @Query("SELECT p.request FROM PlayArea p")
    List<String> findAllJsonRequests();

    @Query("SELECT p.id, p.request FROM PlayArea p WHERE p.status = 'Accepted'")
    List<Object[]> findAllIdAndJsonRequests();

}
