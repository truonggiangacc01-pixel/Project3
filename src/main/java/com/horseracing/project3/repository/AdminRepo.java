package com.horseracing.project3.repository;

import com.horseracing.project3.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<Admin> findByEmail(String email);
}
