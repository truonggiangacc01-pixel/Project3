package com.horseracing.project3.controller;

import com.horseracing.project3.entity.PaymentTransaction;
import com.horseracing.project3.entity.Ticket;
import com.horseracing.project3.enums.PaymentGateway;
import com.horseracing.project3.repository.PaymentTransactionRepo;
import com.horseracing.project3.service.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "*")
@Tag(name = "API Ticket (Purchase & Check-in)")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaymentTransactionRepo paymentTransactionRepo;

    // Class Request Body phụ trợ
    public static class PurchaseRequest {
        public Integer spectatorId;
        public Integer tournamentId;
        public PaymentGateway gateway; // VNPAY, MOMO
        public BigDecimal price;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody PurchaseRequest request) {
        try {
            Ticket ticket = ticketService.purchaseTicket(
                    request.spectatorId,
                    request.tournamentId,
                    request.gateway,
                    request.price
            );
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Dùng cho Check-in tại cổng sự kiện
    @PostMapping("/validate")
    public ResponseEntity<?> validateTicket(@RequestParam String ticketCode) {
        try {
            Ticket validatedTicket = ticketService.validateTicket(ticketCode);
            return new ResponseEntity<>(validatedTicket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Từ chối nếu Used, Expired, Cancelled
        }
    }

    // Lịch sử giao dịch mua vé của 1 khán giả
    @GetMapping("/spectators/{spectatorId}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable Integer spectatorId) {
        try {
            List<PaymentTransaction> transactions = paymentTransactionRepo.findBySpectatorIdOrderByTransactionDateDesc(spectatorId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
