package com.horseracing.project3.service;

import com.horseracing.project3.entity.PaymentTransaction;
import com.horseracing.project3.entity.Spectator;
import com.horseracing.project3.entity.Ticket;
import com.horseracing.project3.entity.Tournament;
import com.horseracing.project3.enums.PaymentGateway;
import com.horseracing.project3.enums.TicketStatus;
import com.horseracing.project3.enums.TransactionStatus;
import com.horseracing.project3.repository.PaymentTransactionRepo;
import com.horseracing.project3.repository.SpectatorRepo;
import com.horseracing.project3.repository.TicketRepo;
import com.horseracing.project3.repository.TournamentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private PaymentTransactionRepo transactionRepo;

    @Autowired
    private SpectatorRepo spectatorRepo;

    @Autowired
    private TournamentRepo tournamentRepo;

    public void saveTicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    @Transactional
    public Ticket purchaseTicket(Integer spectatorId, Integer tournamentId, PaymentGateway gateway, BigDecimal price) {
        Spectator spectator = spectatorRepo.findById(spectatorId)
                .orElseThrow(() -> new RuntimeException("Spectator not found"));
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if (Boolean.FALSE.equals(tournament.getRegistrationOpen())) {
            throw new RuntimeException("Tournament registration is closed. You cannot purchase tickets at this time.");
        }

        // Mô phỏng gọi API cổng thanh toán VNPay/MoMo ở đây
        boolean paymentSuccess = true; // Giả lập thanh toán luôn thành công

        if (!paymentSuccess) {
            PaymentTransaction failedTx = new PaymentTransaction(spectator, null, price, TransactionStatus.FAILED, gateway);
            transactionRepo.save(failedTx);
            throw new RuntimeException("Payment failed via " + gateway);
        }

        // Tạo Ticket (BR-43: Tạo ticket_code unique)
        Ticket newTicket = new Ticket();
        newTicket.setTicketCode(UUID.randomUUID().toString());
        newTicket.setPrice(price);
        newTicket.setStatus(TicketStatus.SOLD); // Mặc định ticket khi mua thành công là SOLD
        newTicket.setPurchaseDate(LocalDateTime.now());
        newTicket.setSpectator(spectator);
        newTicket.setTournament(tournament);

        Ticket savedTicket = ticketRepo.save(newTicket);

        // Lưu Transaction
        PaymentTransaction successTx = new PaymentTransaction(spectator, savedTicket, price, TransactionStatus.SUCCESS, gateway);
        transactionRepo.save(successTx);

        return savedTicket;
    }

    @Transactional
    public Ticket validateTicket(String ticketCode) {
        Ticket ticket = ticketRepo.findByTicketCode(ticketCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // BR-42: Vé ở trạng thái Used, Expired hoặc Cancelled KHÔNG được phép tái sử dụng
        if (ticket.getStatus() != TicketStatus.SOLD) {
            throw new RuntimeException("Invalid ticket status: " + ticket.getStatus());
        }

        // Cập nhật trạng thái vé thành Used khi check-in thành công
        ticket.setStatus(TicketStatus.USED);
        return ticketRepo.save(ticket);
    }
}
