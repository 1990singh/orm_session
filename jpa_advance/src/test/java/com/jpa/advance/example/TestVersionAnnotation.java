package com.jpa.advance.example;


import com.google.inject.Provider;

import org.joda.time.LocalDateTime;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import fk.sp.common.extensions.jpa.Retry;
import com.jpa.advance.example.interal.OrderRepository;
import com.jpa.advance.example.model.OrderEntity;

import static junit.framework.Assert.assertEquals;

/**
 * Created by adarsh.m on 22/01/15.
 */

@RunWith(JukitoRunner.class)
@UseModules(TestModule.class)
public class TestVersionAnnotation {

  @Inject
  private OrderRepository repository;

  @Inject
  private Provider<EntityManager> entityManagerProvider;


  @Test(expected = ObjectOptimisticLockingFailureException.class)
  @Transactional
  public void checkLockModeType() {

    createOrder();
    final OrderEntity order = repository.findOrderByExternalId("OD1");
    assertEquals(0, order.getVersion());

    Thread t = new Thread() {
      @Override
      public void run() {
        updatedOrder();
      }
    };

    t.start();
    sleepy();

    repository.updateStatus(order, OrderEntity.Status.PAYMENT_APPROVED);

  }

  private void sleepy() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updatedOrder() {
    OrderEntity order1 = repository.findOrderByExternalId("OD1");
    repository.updateStatus(order1, OrderEntity.Status.CANCELLED);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
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
