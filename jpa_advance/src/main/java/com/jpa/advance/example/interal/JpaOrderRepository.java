package com.jpa.advance.example.interal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;
import com.jpa.advance.example.model.OrderEntity;
import com.jpa.advance.example.model.OrderHistory;

/**
 * Created by adarsh.m on 21/01/15.
 */
public class JpaOrderRepository extends SimpleJpaGenericRepository<OrderEntity, Long>
    implements OrderRepository {

  @Inject
  public JpaOrderRepository(Provider<EntityManager> entityManagerProvider) {
    super(entityManagerProvider);
  }


  @Override
  public void updateStatus(OrderEntity order, OrderEntity.Status newStatus) {
    OrderEntity.Status currentStatus = order.getStatus();
    order.getHistory().add(new OrderHistory(currentStatus, newStatus));
    order.setStatus(newStatus);
  }

  @Override
  public List<OrderEntity> findOrderByStatus(OrderEntity.Status status) {

    TypedQuery<OrderEntity>
        query =
        getEntityManager().createNamedQuery("findOrderByStatus", OrderEntity.class);
    query.setParameter("status", status);

    return query.getResultList();
  }


  @Override
  public OrderEntity findOrderByExternalId(String externalOrderId) {

    TypedQuery<OrderEntity>
        query =
        getEntityManager().createNamedQuery("findOrderByExternalId", OrderEntity.class);
    query.setParameter("externalOrderId", externalOrderId);

    return query.getSingleResult();

  }

}
