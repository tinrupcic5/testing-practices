package com.example.demo.domain.repository;

import com.example.demo.domain.model.Payment;
import java.util.List;
import java.util.Optional;

public interface IPaymentRepository {

   Payment save(Payment payment);

   List<Payment> findAll();

   Optional<Payment> findById(Long id);

   void deleteById(Long id);
}
