package com.playpals.slotservice.repository;


import com.playpals.slotservice.model.PlayArea;
import com.playpals.slotservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Here you can define custom query methods, e.g., findByUsername
    User findByUsername(String username);


    // Add other query methods if needed
}
