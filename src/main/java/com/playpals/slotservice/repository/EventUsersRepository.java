package com.playpals.slotservice.repository;

import com.playpals.slotservice.model.EventUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventUsersRepository extends JpaRepository<EventUsers,Integer> {
    void deleteByEventIdIn(List<Integer> eventIds);
}
