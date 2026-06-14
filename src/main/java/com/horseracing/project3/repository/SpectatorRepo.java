package com.horseracing.project3.repository;

import com.horseracing.project3.entity.Spectator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpectatorRepo extends JpaRepository<Spectator, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Spectator> findByEmail(String email);
}
