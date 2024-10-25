package com.example.demo.domain.service;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.repository.IPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static com.example.demo.domain.model.PaymentMethod.CARD;
import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private IPaymentRepository paymentRepository;
    @Mock
    private PaymentProcessor cardPaymentProcessor;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCardPayment() {
        // Given
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(100.0));
        payment.setPaymentMethod(CARD.name());

        // When
        doAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setStatus(COMPLETED.name());
            return null;
        }).when(cardPaymentProcessor).processPayment(any());

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment savedPayment = paymentService.createPayment(payment);

        // Then
        assertNotNull(savedPayment);
        assertEquals(COMPLETED.name(), savedPayment.getStatus());
        verify(paymentRepository).save(savedPayment);
        verify(cardPaymentProcessor).processPayment(savedPayment);
    }


    @Test
    void shouldReturnAllPayments() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100.0));
        payment.setPaymentMethod(CARD.name());

        // When
        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(payment));

        var payments = paymentService.getAllPayments();

        // Then
        assertEquals(1, payments.size());
        assertEquals(payment, payments.get(0));
    }

    @Test
    void shouldReturnPaymentById() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100.0));
        payment.setPaymentMethod(CARD.name());

        // When
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        Optional<Payment> foundPayment = paymentService.getPaymentById(1L);

        // Then
        assertTrue(foundPayment.isPresent());
        assertEquals(payment, foundPayment.get());
    }

    @Test
    void shouldReturnEmptyPaymentByIdWhenPaymentNotFound() {
        // When
        when(paymentRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Payment> foundPayment = paymentService.getPaymentById(2L);

        // Then
        assertFalse(foundPayment.isPresent());
    }

    @Test
    void shouldReturnTrueWhenPaymentDeleted() {
        // When
        when(paymentRepository.deleteById(1L)).thenReturn(true);

        boolean deleted = paymentService.deletePayment(1L);

        // Then
        assertTrue(deleted);
        verify(paymentRepository).deleteById(1L);
    }

    @Test
    void shouldReturnFalseWhenPaymentNotDeleted() {
        // When
        when(paymentRepository.deleteById(2L)).thenReturn(false);

        boolean deleted = paymentService.deletePayment(2L);

        // Then
        assertFalse(deleted);
        verify(paymentRepository).deleteById(2L);
    }
}