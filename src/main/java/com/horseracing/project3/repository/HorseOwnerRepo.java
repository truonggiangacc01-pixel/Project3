package com.horseracing.project3.repository;


import com.horseracing.project3.entity.HorseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HorseOwnerRepo extends JpaRepository<HorseOwner, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<HorseOwner> findByUserName(String userName);
    Optional<HorseOwner> findByEmail(String email);
}
