package com.example.demo.domain.component;

import com.example.demo.domain.model.Payment;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardPaymentProcessorTest {

  private CardPaymentProcessor cardPaymentProcessor;

  @BeforeEach
  void setUp() {
    cardPaymentProcessor = new CardPaymentProcessor();
  }

  @Test
  void processPayment_shouldSetStatusToCompleted() {
    // given
    Payment payment = new Payment(BigDecimal.valueOf(100));

    // when
    cardPaymentProcessor.processPayment(payment);

    // then
    assertEquals(COMPLETED.name(), payment.getStatus(), "Payment status should be COMPLETED");
  }
}
