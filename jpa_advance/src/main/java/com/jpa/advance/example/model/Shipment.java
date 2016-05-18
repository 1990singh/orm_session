package com.jpa.advance.example.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by adarsh.m on 21/01/15.
 */
@Data
@Entity
public class Shipment {

  @Id
  @GeneratedValue
  @Column(name="ID")
  private Long id;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      joinColumns={@JoinColumn(name="shipment", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="orderItem", referencedColumnName="ID")})
  private List<OrderItem> items = newArrayList();

}
