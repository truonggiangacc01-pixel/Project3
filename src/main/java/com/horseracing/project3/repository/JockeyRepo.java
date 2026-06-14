package com.horseracing.project3.repository;


import com.horseracing.project3.entity.Jockey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JockeyRepo extends JpaRepository<Jockey, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Jockey> findByEmail(String email);
}
