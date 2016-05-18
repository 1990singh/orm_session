package com.jpa.advance.example.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import lombok.Data;

/**
 * Created by adarsh.m on 21/01/15.
 */

@Data
@Entity
public class OrderItem {


  @Id
  @GeneratedValue
  @Column(name="ID")
  private Long id;

  @NotNull
  private String fsn;

  @Min(1)
  private int quantity;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @ManyToMany(mappedBy = "items")
  private List<Shipment> shipments = newArrayList();

}
