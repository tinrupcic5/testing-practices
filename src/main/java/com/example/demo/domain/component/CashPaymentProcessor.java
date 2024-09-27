package com.example.demo.domain.component;

import static com.example.demo.domain.model.PaymentStatus.REJECTED;

import com.example.demo.domain.annotation.PaymentType;
import com.example.demo.domain.model.Payment;
import com.example.demo.domain.model.PaymentMethod;
import com.example.demo.domain.service.PaymentProcessor;
import org.springframework.stereotype.Component;

@Component
@PaymentType(PaymentMethod.CASH)
public class CashPaymentProcessor implements PaymentProcessor {


  @Override
  public void processPayment(Payment payment) {
    System.out.println("Processing Cash Payment: " + payment.getAmount());
    payment.setStatus(REJECTED.name());
  }
}
