package com.playpals.slotservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playpals.slotservice.model.Courts;

public interface CourtRepository extends  JpaRepository<Courts, Integer>{

}
