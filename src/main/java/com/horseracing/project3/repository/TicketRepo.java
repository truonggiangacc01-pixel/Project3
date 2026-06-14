package com.horseracing.project3.repository;

import com.horseracing.project3.entity.HorseOwner;
import com.horseracing.project3.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Integer> {
}
