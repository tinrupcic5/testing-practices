package com.example.demo.domain.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.model.Payment;
import com.example.demo.domain.service.IPaymentService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {

  private MockMvc mockMvc;

  @Mock
  private IPaymentService paymentService;

  @InjectMocks
  private PaymentController paymentController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
  }

  @Test
  void createPayment_ShouldReturnCreatedPayment() throws Exception {
    // given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus("COMPLETED");

    // when
    when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

    // then
    mockMvc.perform(post("/api/payments")
            .contentType("application/json")
            .content(""
                + "{\"amount\": 100.0,"
                + " \"status\": \"COMPLETED\"}"
                + ""))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.amount").value(100.0))
        .andExpect(jsonPath("$.status").value("COMPLETED"));
  }

  @Test
  void getAllPayments_ShouldReturnListOfPayments() throws Exception {
    // given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus("COMPLETED");

    // when
    when(paymentService.getAllPayments()).thenReturn(Collections.singletonList(payment));

    // then
    mockMvc.perform(get("/api/payments"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].amount").value(100.0))
        .andExpect(jsonPath("$[0].status").value("COMPLETED"));
  }

  @Test
  void getPaymentById_ShouldReturnPayment_WhenExists() throws Exception {
    // given
    Payment payment = new Payment();
    payment.setId(1L);
    payment.setAmount(BigDecimal.valueOf(100));
    payment.setStatus("COMPLETED");

    // when
    when(paymentService.getPaymentById(anyLong())).thenReturn(Optional.of(payment));

    // then
    mockMvc.perform(get("/api/payments/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.amount").value(100.0))
        .andExpect(jsonPath("$.status").value("COMPLETED"));
  }

  @Test
  void getPaymentById_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
    // when
    when(paymentService.getPaymentById(anyLong())).thenReturn(Optional.empty());

    // then
    mockMvc.perform(get("/api/payments/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void deletePayment_ShouldReturnNoContent() throws Exception {
    // when
    doNothing().when(paymentService).deletePayment(anyLong());

    // then
    mockMvc.perform(delete("/api/payments/1"))
        .andExpect(status().isNoContent());

    verify(paymentService, times(1)).deletePayment(1L);
  }
}
