package com.horseracing.project3.repository;

import com.horseracing.project3.entity.Horse;
import com.horseracing.project3.entity.HorseOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorseRepo extends JpaRepository<Horse, Integer> {
    List<Horse> findByHorseOwner_Email(String email);
    boolean existsByName(String name);
}
