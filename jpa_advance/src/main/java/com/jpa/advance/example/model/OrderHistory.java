package com.jpa.advance.example.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Created by adarsh.m on 21/01/15.
 */

@Data
@Entity
public class OrderHistory {

  public OrderHistory() {
  }

  public OrderHistory(OrderEntity.Status from, OrderEntity.Status to) {
    this.fromStatus = from;
    this.toStatus = to;
    this.changeDate = new LocalDateTime();
  }

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderEntity.Status fromStatus;

  @Enumerated(EnumType.STRING)
  private OrderEntity.Status toStatus;

  @Column
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
  private LocalDateTime changeDate;
}
