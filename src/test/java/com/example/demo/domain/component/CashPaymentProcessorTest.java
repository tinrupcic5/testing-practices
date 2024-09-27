package com.example.demo.domain.component;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static com.example.demo.domain.model.PaymentStatus.REJECTED;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.domain.model.Payment;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CashPaymentProcessorTest {
  private CashPaymentProcessor cashPaymentProcessor;

  @BeforeEach
  void setUp() {
    cashPaymentProcessor = new CashPaymentProcessor();
  }

  @Test
  void processPayment_shouldSetStatusToRejected() {
    // given
    Payment payment = new Payment(BigDecimal.valueOf(100));

    // when
    cashPaymentProcessor.processPayment(payment);

    // then
    assertEquals(REJECTED.name(), payment.getStatus(), "Payment status should be REJECTED");
  }
}
