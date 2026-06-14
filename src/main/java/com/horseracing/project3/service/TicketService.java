package com.horseracing.project3.service;

import com.horseracing.project3.entity.Ticket;
import com.horseracing.project3.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    public void saveTicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }
}
