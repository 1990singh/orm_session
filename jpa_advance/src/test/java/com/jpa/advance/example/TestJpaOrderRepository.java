package com.jpa.advance.example;

import com.google.inject.Provider;

import org.joda.time.LocalDateTime;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import com.jpa.advance.example.interal.OrderRepository;
import com.jpa.advance.example.model.OrderEntity;

import fk.sp.common.extensions.jpa.Retry;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by adarsh.m on 21/01/15.
 */


@RunWith(JukitoRunner.class)
@UseModules(TestModule.class)
@Transactional
public class TestJpaOrderRepository {

  @Inject
  private OrderRepository repository;

  @Inject
  private Provider<EntityManager> entityManagerProvider;

  @Before
  public void setup() {
    prepareSampleOrders();
  }


  @Test
  public void shouldFindOrderByStatus() {
    List<OrderEntity> orderByStatus = repository.findOrderByStatus(OrderEntity.Status.DELIVERED);


    assertFalse(orderByStatus.isEmpty());
    assertEquals(OrderEntity.Status.DELIVERED, orderByStatus.get(0).getStatus());
  }

  @Test
  //@Retry()
  public void shouldUpdateOrderEntity() {

    List<OrderEntity> orderByStatus = repository.findOrderByStatus(OrderEntity.Status.CREATED);

    repository.updateStatus(orderByStatus.get(0), OrderEntity.Status.PAYMENT_APPROVED);

    repository.updateStatus(orderByStatus.get(0), OrderEntity.Status.CANCELLED);

    List<OrderEntity> cancelledOrder = repository.findOrderByStatus(OrderEntity.Status.CANCELLED);

    assertNotNull(cancelledOrder.get(0));

  }

  @Test()
  public void shouldThrowExceptionWithVersion() {

    EntityManager entityManager = entityManagerProvider.get();

    OrderEntity order = repository.findOrderByExternalId("OD1");

    repository.updateStatus(order, OrderEntity.Status.CANCELLED);

    entityManager.flush();
    //close() or detach() or clear()
    entityManager.clear();


    //cloned object from other sources or transaction thread from thread pool
    OrderEntity clonedObject = getClonedObject(order.getId());

    repository.merge(clonedObject);

  }

  @Test
  //@Ignore
  public void shouldSucceedWithoutVersion() {

    EntityManager entityManager = entityManagerProvider.get();

    OrderEntity order = repository.findOrderByExternalId("OD1");

    repository.updateStatus(order, OrderEntity.Status.CANCELLED);

    entityManager.flush();
    //close() or detach() or clear()
    entityManager.clear();


    //cloned object from other sources or transaction thread from thread pool
    OrderEntity clonedObject = getClonedObject(order.getId());

    repository.merge(clonedObject);

    OrderEntity updatedObject = repository.findOrderByExternalId("OD1");

    assertNull(updatedObject.getCustomerName());
    assertEquals(OrderEntity.Status.CREATED, updatedObject.getStatus());

  }


  private OrderEntity getClonedObject(Long id) {
    OrderEntity clonedObject = new OrderEntity();
    clonedObject.setId(id);
    clonedObject.setExternalOrderId("OD1");
    clonedObject.setStatus(OrderEntity.Status.CREATED);
    clonedObject.setCreated(LocalDateTime.now());
    clonedObject.setOrderValue(BigDecimal.valueOf(1000));
    clonedObject.setCustomerName(null);
    return clonedObject;
  }


  private void prepareSampleOrders() {
    OrderEntity order1 = new OrderEntity();

    order1.setExternalOrderId("OD1");
    order1.setStatus(OrderEntity.Status.CREATED);
    order1.setCreated(LocalDateTime.now());
    order1.setCustomerName("test");
    order1.setOrderValue(BigDecimal.valueOf(1000));

    repository.persist(order1);

    OrderEntity order2 = new OrderEntity();

    order2.setExternalOrderId("OD2");
    order2.setStatus(OrderEntity.Status.DELIVERED);
    order2.setCreated(LocalDateTime.now());
    order2.setOrderValue(BigDecimal.valueOf(1000));

    repository.persist(order2);
  }
}
