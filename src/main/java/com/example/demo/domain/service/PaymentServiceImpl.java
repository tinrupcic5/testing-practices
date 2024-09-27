package com.example.demo.domain.service;

import com.example.demo.domain.annotation.PaymentType;
import com.example.demo.domain.model.Payment;
import com.example.demo.domain.model.PaymentMethod;
import com.example.demo.domain.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements IPaymentService {

  private IPaymentRepository paymentRepository;

  @PaymentType(PaymentMethod.CARD)
  private PaymentProcessor cardPaymentProcessor;

  @PaymentType(PaymentMethod.PAYPAL)
  private PaymentProcessor payPalPaymentProcessor;


  @Autowired
  public PaymentServiceImpl(IPaymentRepository paymentRepository, PaymentProcessor cardPaymentProcessor,
      PaymentProcessor payPalPaymentProcessor) {
    this.paymentRepository = paymentRepository;
    this.cardPaymentProcessor = cardPaymentProcessor;
    this.payPalPaymentProcessor = payPalPaymentProcessor;
  }

  @Override
  public Payment createPayment(Payment payment) {
    payment.setPaymentDate(new Date());
    payment.setStatus("PENDING");

    PaymentProcessor processor = getProcessor(payment.getPaymentMethod());
    processor.processPayment(payment);

    return paymentRepository.save(payment);
  }

  @Override
  public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
  }

  @Override
  public Optional<Payment> getPaymentById(Long id) {
    return paymentRepository.findById(id);
  }

  @Override
  public void deletePayment(Long id) {
    paymentRepository.deleteById(id);
  }

  private PaymentProcessor getProcessor(String method) {
    PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
    return switch (paymentMethod) {
      case CARD -> cardPaymentProcessor;
      case PAYPAL -> payPalPaymentProcessor;
      default -> throw new IllegalArgumentException("Invalid payment method: " + method);
    };
  }
}
