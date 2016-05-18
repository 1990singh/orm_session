package com.jpa.advance.example.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import fk.sp.common.extensions.jpa.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by adarsh.m on 21/01/15.
 */


@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class OrderEntity extends Auditable {

  public static enum Status {
    CREATED, PAYMENT_APPROVED, CANCELLED, DELIVERED
  }

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String externalOrderId;

  //@Version
  private int version;

  private String customerName;

  private BigDecimal orderValue;

  @Enumerated(EnumType.STRING)
  private Status status = Status.CREATED;

  @Column
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
  private LocalDateTime created;

  @Column
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
  private LocalDateTime updated;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private List<OrderItem> items = newArrayList();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id")
  private List<OrderHistory> history = newArrayList();


  @AssertTrue(message = "Order value cannot be zero")
  public boolean isOrderValueValid() {
    return !orderValue.equals(BigDecimal.ZERO);
  }

}
