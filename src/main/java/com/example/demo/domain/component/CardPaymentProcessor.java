package com.example.demo.domain.component;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;

import com.example.demo.domain.annotation.PaymentType;
import com.example.demo.domain.model.Payment;
import com.example.demo.domain.model.PaymentMethod;
import com.example.demo.domain.service.PaymentProcessor;
import org.springframework.stereotype.Component;

@Component
@PaymentType(PaymentMethod.CARD)
public class CardPaymentProcessor implements PaymentProcessor {


  @Override
  public void processPayment(Payment payment) {
    System.out.println("Processing Card Payment: " + payment.getAmount());

    payment.setStatus(COMPLETED.name());
  }
}
