package com.example.demo.domain.controller;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.service.IPaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = PaymentController.class)
class PaymentControllerAPITest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IPaymentService paymentService;

  @Test
  public void shouldReturnAllPayments() throws Exception {
    // Given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus(COMPLETED.name());

    // Given
    given(paymentService.getAllPayments()).willReturn(Collections.singletonList(payment));

    // Then
    mockMvc.perform(get("/api/payments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].status").value(COMPLETED.name()));
  }

  @Test
  public void shouldCreatePayment() throws Exception {
    // Given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus(COMPLETED.name());

    // When
    given(paymentService.createPayment(any(Payment.class))).willReturn(payment);

    // Then
    mockMvc.perform(post("/api/payments")
                    .contentType("application/json")
                    .content(""
                            + "{\"amount\": 100.0,"
                            + " \"status\": \"COMPLETED\"}"
                            + ""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.status").value(COMPLETED.name()));
  }

  @Test
  public void shouldGetPaymentById() throws Exception {
    // Given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus(COMPLETED.name());

    // When
    given(paymentService.getPaymentById(anyLong())).willReturn(Optional.of(payment));

    // Then
    mockMvc.perform(get("/api/payments/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.status").value(COMPLETED.name()));
  }

  @Test
  void ShouldReturnNotFoundWhenGetPaymentByIdDoesNotExist() throws Exception {
    // Given
    given(paymentService.getPaymentById(anyLong())).willReturn(Optional.empty());

    // When & Then
    mockMvc.perform(get("/api/payments/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  void ShouldReturnOkIfDeleted() throws Exception {
    // Given
    given(paymentService.deletePayment(anyLong())).willReturn(true);

    // When & Then
    mockMvc.perform(delete("/api/payments/1"))
            .andExpect(status().isOk());
  }

  @Test
  void ShouldReturnNotFoundWhenDeleting() throws Exception {
    // Given
    given(paymentService.deletePayment(anyLong())).willReturn(false);

    // When & Then
    mockMvc.perform(delete("/api/payments/1"))
            .andExpect(status().isNotFound());
  }


}
