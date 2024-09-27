package com.example.demo.domain.service;

import com.example.demo.domain.model.Payment;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {

  Payment createPayment(Payment payment);

  List<Payment> getAllPayments();

  Optional<Payment> getPaymentById(Long id);

  void deletePayment(Long id);
}
