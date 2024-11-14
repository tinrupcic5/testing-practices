package com.example.demo.domain.repository;

import com.example.demo.domain.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.example.demo.domain.model.PaymentMethod.CARD;
import static com.example.demo.domain.model.PaymentMethod.PAYPAL;
import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Rollback
public class JpaPaymentRepositoryIntegrationTest {

    private final IPaymentRepository paymentRepository;

    private Payment payment;

    public JpaPaymentRepositoryIntegrationTest(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @BeforeEach
    public void setUp() {
        payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100.00));
        payment.setCurrency("USD");
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(PAYPAL.name());
        payment.setStatus(COMPLETED.name());
    }


    @Test
    public void shouldSavePayment() {
        // When
        Payment savedPayment = paymentRepository.save(payment);

        // Then
        assertThat(savedPayment).isNotNull();
        assertThat(savedPayment.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindPaymentById() {
        // When
        Payment savedPayment = paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById(savedPayment.getId()).orElse(null);
        // Then
        assertThat(foundPayment).isNotNull();
        assertThat(foundPayment.getId()).isEqualTo(savedPayment.getId());
    }

    @Test
    public void shouldFindAllPayments() {
        // When
        paymentRepository.save(payment);

        Payment payment2 = new Payment();
        payment2.setAmount(BigDecimal.valueOf(100.00));
        payment2.setCurrency("EUR");
        payment2.setPaymentDate(new Date());
        payment2.setPaymentMethod(CARD.name());
        payment2.setStatus(COMPLETED.name());
        paymentRepository.save(payment2);

        List<Payment> payments = paymentRepository.findAll();

        // Then
        assertThat(payments).hasSize(2);
    }

    @Test
    public void shouldDeletePaymentById() {
        // When
        Payment savedPayment = paymentRepository.save(payment);
        Long paymentId = savedPayment.getId();

        // Then
        paymentRepository.deleteById(paymentId);
        assertThat(paymentRepository.findById(paymentId)).isNotPresent();
    }
}
