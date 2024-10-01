package com.example.demo.domain.controller;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private IPaymentService paymentService;

  @Autowired
  public PaymentController(IPaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
    Payment createdPayment = paymentService.createPayment(payment);
    return ResponseEntity.ok(createdPayment);
  }

  @GetMapping
  public ResponseEntity<List<Payment>> getAllPayments() {
    List<Payment> payments = paymentService.getAllPayments();
    return ResponseEntity.ok(payments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
    return paymentService.getPaymentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentService.deletePayment(id);
    return ResponseEntity.noContent().build();
  }
}
