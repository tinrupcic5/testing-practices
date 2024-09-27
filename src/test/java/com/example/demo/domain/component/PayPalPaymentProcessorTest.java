package com.example.demo.domain.component;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.domain.model.Payment;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayPalPaymentProcessorTest {
  private PayPalPaymentProcessor payPalPaymentProcessor;

  @BeforeEach
  void setUp() {
    payPalPaymentProcessor = new PayPalPaymentProcessor();
  }

  @Test
  void processPayment_shouldSetStatusToCompleted() {
    // given
    Payment payment = new Payment(BigDecimal.valueOf(100));

    // when
    payPalPaymentProcessor.processPayment(payment);

    // then
    assertEquals(COMPLETED.name(), payment.getStatus(), "Payment status should be COMPLETED");
  }
}
