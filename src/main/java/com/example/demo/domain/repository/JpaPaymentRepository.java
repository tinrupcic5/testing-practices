package com.example.demo.domain.repository;


import com.example.demo.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

}
