package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailsRepository extends JpaRepository<Details, Long> {
}
