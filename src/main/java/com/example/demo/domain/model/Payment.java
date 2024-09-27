package com.example.demo.domain.model;

import static com.example.demo.domain.model.PaymentStatus.COMPLETED;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private String currency;

  @Column(nullable = false)
  private Date paymentDate;

  @Column(nullable = false)
  private String status; //  "PENDING", "COMPLETED", "FAILED"

  @Column(nullable = false)
  private String paymentMethod;

  @Column
  @JsonIgnore
  private boolean isPayed = false;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
    if (COMPLETED.name().equals(status)) {
      this.isPayed = true;
    }
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public boolean isPayed() {
    return isPayed;
  }

  public void setPayed(boolean payed) {
    isPayed = payed;
  }
}
