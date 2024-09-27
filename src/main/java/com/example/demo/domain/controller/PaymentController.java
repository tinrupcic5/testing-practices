package com.example.demo.domain.controller;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.service.IPaymentService;
import com.example.demo.domain.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private IPaymentService paymentServiceImpl;

  @Autowired
  public PaymentController(IPaymentService paymentServiceImpl) {
    this.paymentServiceImpl = paymentServiceImpl;
  }

  @PostMapping
  public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
    Payment createdPayment = paymentServiceImpl.createPayment(payment);
    return ResponseEntity.ok(createdPayment);
  }

  @GetMapping
  public ResponseEntity<List<Payment>> getAllPayments() {
    List<Payment> payments = paymentServiceImpl.getAllPayments();
    return ResponseEntity.ok(payments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
    return paymentServiceImpl.getPaymentById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    paymentServiceImpl.deletePayment(id);
    return ResponseEntity.noContent().build();
  }
}
