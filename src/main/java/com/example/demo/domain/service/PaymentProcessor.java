package com.example.demo.domain.service;

import com.example.demo.domain.model.Payment;

public interface PaymentProcessor {
  void processPayment(Payment payment);
}
