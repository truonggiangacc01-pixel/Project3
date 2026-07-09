package com.horseracing.project3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.horseracing.project3.entity.PaymentTransaction;
import com.horseracing.project3.enums.TicketStatus;
import com.horseracing.project3.enums.TransactionStatus;
import com.horseracing.project3.repository.PaymentTransactionRepo;
import com.horseracing.project3.repository.TicketRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.webhooks.Webhook;
import vn.payos.model.webhooks.WebhookData;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin(origins = "*")
@Tag(name = "API Payment (PayOS)")
public class PaymentController {

    @Autowired
    private PayOS payOS;

    @Autowired
    private PaymentTransactionRepo transactionRepo;

    @Autowired
    private TicketRepo ticketRepo;

    @PostMapping("/webhook")
    public ObjectNode payosWebhookHandler(@RequestBody ObjectNode body) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        try {
            // Xác thực Webhook bằng thư viện PayOS v2
            // Hàm verify() yêu cầu truyền vào một object Webhook
            Webhook webhookBody = new Webhook(
                    body.get("code").asText(),
                    body.get("desc").asText(),
                    body.get("success").asBoolean(),
                    mapper.treeToValue(body.get("data"), WebhookData.class),
                    body.get("signature").asText()
            );
            
            WebhookData data = payOS.webhooks().verify(webhookBody);

            if (data != null) {
                // Lấy thông tin giao dịch
                Long orderCode = data.getOrderCode();
                String statusStr = data.getCode(); // Tùy thuộc vào version PayOS, thường là "00" cho thành công
                
                // Cập nhật DB
                updatePaymentStatusInDB(orderCode, "00".equals(statusStr));
                
                response.put("success", true);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", e.getMessage());
            return response;
        }
        response.put("success", false);
        return response;
    }

    private void updatePaymentStatusInDB(Long orderCode, boolean isSuccess) {
        // Tìm transaction dựa trên ID (do chúng ta dùng ID làm orderCode)
        PaymentTransaction tx = transactionRepo.findById(orderCode.intValue()).orElse(null);
        if (tx != null && tx.getStatus() == TransactionStatus.PENDING) {
            if (isSuccess) {
                tx.setStatus(TransactionStatus.SUCCESS);
                if (tx.getTicket() != null) {
                    tx.getTicket().setStatus(TicketStatus.SOLD);
                    ticketRepo.save(tx.getTicket());
                }
            } else {
                tx.setStatus(TransactionStatus.FAILED);
                if (tx.getTicket() != null) {
                    tx.getTicket().setStatus(TicketStatus.CANCELLED);
                    ticketRepo.save(tx.getTicket());
                }
            }
            transactionRepo.save(tx);
        }
    }

    @GetMapping("/status/{orderCode}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable Integer orderCode) {
        PaymentTransaction tx = transactionRepo.findById(orderCode).orElse(null);
        if (tx == null) {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"Không tìm thấy giao dịch!\"}");
        }
        return ResponseEntity.ok(tx);
    }

    @Autowired
    private com.horseracing.project3.service.TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> createPaymentLink(@RequestBody com.horseracing.project3.controller.TicketController.PurchaseRequest request) {
        try {
            Object result = ticketService.purchaseTicket(
                    request.spectatorId,
                    request.tournamentId,
                    request.gateway,
                    request.price
            );
            return new ResponseEntity<>(result, org.springframework.http.HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{orderCode}")
    public ResponseEntity<?> cancelPaymentLink(@PathVariable Integer orderCode) {
        try {
            // Cancel on PayOS
            vn.payos.model.v2.paymentRequests.PaymentLink paymentLink = payOS.paymentRequests().cancel(orderCode.longValue(), "Người dùng yêu cầu hủy");

            // Update DB
            updatePaymentStatusInDB(orderCode.longValue(), false);

            return ResponseEntity.ok(paymentLink);
        } catch (Exception e) {
            // Update DB locally anyway if PayOS fails (maybe it was already paid/cancelled)?
            updatePaymentStatusInDB(orderCode.longValue(), false);
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}
