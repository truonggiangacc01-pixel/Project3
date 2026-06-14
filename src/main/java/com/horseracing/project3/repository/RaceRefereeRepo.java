package com.horseracing.project3.repository;

import com.horseracing.project3.entity.RaceReferee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRefereeRepo extends JpaRepository<RaceReferee, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<RaceReferee> findByEmail(String email);
}
