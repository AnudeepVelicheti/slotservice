package com.playpals.slotservice.repository;



import com.playpals.slotservice.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SportsRepository extends JpaRepository<Sport, Integer> {

    @Query(value="SELECT s.* FROM sports s WHERE s.name =:name",nativeQuery = true)
    Sport getByName(String name);


}

