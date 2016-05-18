package com.jpa.advance.example;

import org.joda.time.LocalDateTime;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.inject.Inject;

import com.jpa.advance.example.interal.OrderRepository;
import com.jpa.advance.example.model.OrderEntity;

/**
 * Created by adarsh.m on 22/01/15.
 */

@RunWith(JukitoRunner.class)
@UseModules(TestModule.class)
@Transactional
public class TestConstraintValidations {

  @Inject
  private OrderRepository repository;


  @Test(expected = TransactionSystemException.class)
  public void shouldThrowException() {
    OrderEntity order = createOrder();
    order.setExternalOrderId(null);
  }

  @Test(expected = TransactionSystemException.class)
  public void shouldThrowValidationException() {
    OrderEntity order = createOrder();
    order.setOrderValue(BigDecimal.ZERO);
  }

  public OrderEntity createOrder() {
    OrderEntity order1 = new OrderEntity();

    order1.setExternalOrderId("OD1");
    order1.setStatus(OrderEntity.Status.CREATED);
    order1.setCreated(LocalDateTime.now());
    order1.setCustomerName("test");
    order1.setOrderValue(BigDecimal.valueOf(1000));

    repository.persist(order1);
    return order1;
  }

}
