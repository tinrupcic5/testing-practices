package com.example.demo.domain.repository;

import com.example.demo.domain.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements IPaymentRepository {

  private final JpaPaymentRepository paymentRepository;

  @Autowired
  public PaymentRepositoryImpl(JpaPaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment save(Payment payment) {
    return paymentRepository.save(payment);

  }

  @Override
  public List<Payment> findAll() {
    return paymentRepository.findAll();
  }

  @Override
  public Optional<Payment> findById(Long id) {
    return paymentRepository.findById(id);
  }

  @Override
  public boolean deleteById(Long id) {
   paymentRepository.deleteById(id);
   return paymentRepository.existsById(id);
  }
}
