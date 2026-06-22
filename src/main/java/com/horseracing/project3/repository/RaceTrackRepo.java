package com.horseracing.project3.repository;

import com.horseracing.project3.entity.RaceTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceTrackRepo extends JpaRepository<RaceTrack, Integer> {
}
