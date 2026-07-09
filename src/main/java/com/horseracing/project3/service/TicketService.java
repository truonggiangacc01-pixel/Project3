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

    @Autowired
    private vn.payos.PayOS payOS;

    @Transactional
    public Object purchaseTicket(Integer spectatorId, Integer tournamentId, PaymentGateway gateway, BigDecimal price) {
        Spectator spectator = spectatorRepo.findById(spectatorId)
                .orElseThrow(() -> new RuntimeException("Spectator not found"));
        Tournament tournament = tournamentRepo.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if (Boolean.FALSE.equals(tournament.getRegistrationOpen())) {
            throw new RuntimeException("Tournament registration is closed. You cannot purchase tickets at this time.");
        }

        // 1. Tạo Ticket (Trạng thái PENDING)
        Ticket newTicket = new Ticket();
        newTicket.setTicketCode(UUID.randomUUID().toString());
        newTicket.setPrice(price);
        newTicket.setStatus(TicketStatus.PENDING);
        newTicket.setPurchaseDate(LocalDateTime.now());
        newTicket.setSpectator(spectator);
        newTicket.setTournament(tournament);

        Ticket savedTicket = ticketRepo.save(newTicket);

        // 2. Tạo Transaction (Trạng thái PENDING)
        PaymentTransaction pendingTx = new PaymentTransaction(spectator, savedTicket, price, TransactionStatus.PENDING, gateway);
        PaymentTransaction savedTx = transactionRepo.save(pendingTx);

        // 3. Gọi PayOS để lấy link thanh toán
        try {
            Long orderCode = savedTx.getId().longValue(); // Sử dụng ID làm orderCode
            
            // Xây dựng request tạo link thanh toán (SDK v2)
            vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest paymentRequest = 
                vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest.builder()
                    .orderCode(orderCode)
                    .amount((long) price.intValue())
                    .description("Thanh toan ve dua ngua")
                    .returnUrl("http://localhost:3000/payment/success")
                    .cancelUrl("http://localhost:3000/payment/cancel")
                    .build();

            // Lấy URL Checkout từ PayOS
            vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse data = payOS.paymentRequests().create(paymentRequest);
            
            // Trả về một Map chứa Ticket tạm và checkoutUrl
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("ticket", savedTicket);
            response.put("checkoutUrl", data.getCheckoutUrl());
            response.put("orderCode", orderCode);
            
            return response;
        } catch (Exception e) {
            // Rollback bằng cách ném Exception
            e.printStackTrace();
            throw new RuntimeException("Lỗi tạo link thanh toán PayOS: " + e.getMessage());
        }
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
