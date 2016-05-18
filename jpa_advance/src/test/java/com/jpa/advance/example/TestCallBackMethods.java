package com.jpa.advance.example;

import org.joda.time.LocalDateTime;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.inject.Inject;

import fk.sp.common.extensions.RequestContext;
import com.jpa.advance.example.interal.OrderRepository;
import com.jpa.advance.example.model.OrderEntity;

import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;

/**
 * Created by adarsh.m on 21/01/15.
 */
@RunWith(JukitoRunner.class)
@UseModules(TestModule.class)
@Transactional
public class TestCallBackMethods {

  @Inject
  private OrderRepository repository;

  @Before
  public void setup() {
    RequestContext.setCurrentUser("Test1");
  }

  @Test
  public void shouldInvokeCallBackMethods() {
    OrderEntity orderEntity = createOrder();

    assertEquals(RequestContext.getCurrentUser(), orderEntity.getUpdatedBy());
    assertEquals(RequestContext.getCurrentUser(), orderEntity.getCreatedBy());

    updateEntity();

    OrderEntity updatedEntity = repository.findOrderByExternalId("OD1");

    assertEquals(RequestContext.getCurrentUser(), updatedEntity.getUpdatedBy());
    assertNotSame(RequestContext.getCurrentUser(), updatedEntity.getCreatedBy());
  }


  public void updateEntity() {
    RequestContext.setCurrentUser("Test2");

    OrderEntity orderEntity = repository.findOrderByExternalId("OD1");
    orderEntity.setStatus(OrderEntity.Status.CANCELLED);

  }

  public OrderEntity createOrder() {
    OrderEntity order1 = new OrderEntity();

    order1.setExternalOrderId("OD1");
    order1.setStatus(OrderEntity.Status.CREATED);
    order1.setCreated(LocalDateTime.now());
    order1.setCustomerName("test");
    order1.setOrderValue(BigDecimal.valueOf(100));

    repository.persist(order1);
    return order1;
  }

}
