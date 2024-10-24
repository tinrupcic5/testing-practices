package com.example.demo.domain.controller;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.service.IPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {


    @Mock
    private IPaymentService paymentService;

    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentController = new PaymentController(paymentService);
    }

    @Test
    public void shouldReturnAllPayments() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setStatus("COMPLETED");

        // When
        List<Payment> expectedPayments = Collections.singletonList(payment);
        given(paymentService.getAllPayments()).willReturn(expectedPayments);
        ResponseEntity<List<Payment>> responseEntity = paymentController.getAllPayments();
        List<Payment> actualPayments = responseEntity.getBody();

        // Then
        assertThat(actualPayments, is(expectedPayments));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldCreatePayment() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setStatus("PENDING");

        Payment createdPayment = new Payment();
        createdPayment.setId(1L);
        createdPayment.setAmount(BigDecimal.valueOf(100));
        createdPayment.setStatus("COMPLETED");

        // When
        given(paymentService.createPayment(any(Payment.class))).willReturn(createdPayment);

        ResponseEntity<Payment> responseEntity = paymentController.createPayment(payment);
        Payment actualPayment = responseEntity.getBody();

        // Then
        assertThat(actualPayment, is(createdPayment));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualPayment != null, is(true));
        assertThat(actualPayment.getId(), is(createdPayment.getId()));
        assertThat(actualPayment.getAmount(), is(createdPayment.getAmount()));
        assertThat(actualPayment.getStatus(), is(createdPayment.getStatus()));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldGetPaymentById() {
        // Given
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(BigDecimal.valueOf(100));
        payment.setStatus("COMPLETED");

        // When
        given(paymentService.getPaymentById(anyLong())).willReturn(Optional.of(payment));

        ResponseEntity<Payment> responseEntity = paymentController.getPaymentById(1L);
        Payment actualPayment = responseEntity.getBody();

        // Then
        assertThat(actualPayment, is(payment));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(actualPayment.getId(), is(payment.getId()));
        assertThat(actualPayment.getAmount(), is(payment.getAmount()));
        assertThat(actualPayment.getStatus(), is(payment.getStatus()));
    }

    @Test
    public void shouldReturnNotFoundWhenGetPaymentById() {
        // Given
        given(paymentService.getPaymentById(anyLong())).willReturn(Optional.empty());

        // When
        ResponseEntity<Payment> responseEntity = paymentController.getPaymentById(1L);

        // Then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(responseEntity.getBody() == null, is(true));
    }


    @Test
    void ShouldReturnOkIfDeleted() {
        // Given
        given(paymentService.deletePayment(anyLong())).willReturn(true);

        ResponseEntity<Boolean> responseEntity = paymentController.deletePayment(anyLong());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void ShouldReturnNotFoundOnDelete() {
        // Given
        given(paymentService.deletePayment(anyLong())).willReturn(false);

        // Then
        ResponseEntity<Boolean> responseEntity = paymentController.deletePayment(anyLong());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }


}
