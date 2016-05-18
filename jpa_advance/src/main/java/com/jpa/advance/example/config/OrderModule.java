package com.jpa.advance.example.config;

import com.google.inject.AbstractModule;

import com.jpa.advance.example.interal.JpaOrderRepository;
import com.jpa.advance.example.interal.OrderRepository;

/**
 * Created by adarsh.m on 21/01/15.
 */
public class OrderModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(OrderRepository.class).to(JpaOrderRepository.class);
  }
}
