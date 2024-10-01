package com.example.demo.domain.service;

import static com.example.demo.domain.model.PaymentStatus.PENDING;

import com.example.demo.domain.annotation.PaymentType;
import com.example.demo.domain.model.Payment;
import com.example.demo.domain.model.PaymentMethod;
import com.example.demo.domain.repository.IPaymentRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements IPaymentService {

  private IPaymentRepository paymentRepository;

  @PaymentType(PaymentMethod.CARD)
  private PaymentProcessor cardPaymentProcessor;

  @PaymentType(PaymentMethod.PAYPAL)
  private PaymentProcessor payPalPaymentProcessor;

  @PaymentType(PaymentMethod.CASH)
  private PaymentProcessor cashPaymentProcessor;


  public PaymentServiceImpl(IPaymentRepository paymentRepository, PaymentProcessor cardPaymentProcessor,
      PaymentProcessor payPalPaymentProcessor, PaymentProcessor cashPaymentProcessor) {
    this.paymentRepository = paymentRepository;
    this.cardPaymentProcessor = cardPaymentProcessor;
    this.payPalPaymentProcessor = payPalPaymentProcessor;
    this.cashPaymentProcessor = cashPaymentProcessor;
  }

  @Override
  public Payment createPayment(Payment payment) {
    payment.setPaymentDate(new Date());
    payment.setStatus(PENDING.name());

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
    try {
      PaymentMethod paymentMethod = PaymentMethod.valueOf(method.toUpperCase());
      return switch (paymentMethod) {
        case PAYPAL -> payPalPaymentProcessor;
        case CARD -> cardPaymentProcessor;
        case CASH -> cashPaymentProcessor;
      };
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid payment method: " + method);
    }
  }

}
