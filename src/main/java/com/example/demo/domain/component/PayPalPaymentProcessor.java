package com.example.demo.domain.component;


import com.example.demo.domain.annotation.PaymentType;
import com.example.demo.domain.model.Payment;
import com.example.demo.domain.model.PaymentMethod;
import com.example.demo.domain.service.PaymentProcessor;
import org.springframework.stereotype.Component;

@Component
@PaymentType(PaymentMethod.PAYPAL)
public class PayPalPaymentProcessor implements PaymentProcessor {



  @Override
  public void processPayment(Payment payment) {
    System.out.println("Processing PayPal Payment: " + payment.getAmount());
    payment.setStatus("COMPLETED");
  }
}
