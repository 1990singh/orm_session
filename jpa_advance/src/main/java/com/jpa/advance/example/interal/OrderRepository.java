package com.jpa.advance.example.interal;

import fk.sp.common.extensions.jpa.JpaGenericRepository;
import com.jpa.advance.example.model.OrderEntity;

import java.util.List;

/**
 * Created by adarsh.m on 21/01/15.
 */
public interface OrderRepository extends JpaGenericRepository<OrderEntity, Long> {

  void updateStatus(OrderEntity order, OrderEntity.Status newStatus);

  List<OrderEntity> findOrderByStatus(OrderEntity.Status status);

  OrderEntity findOrderByExternalId(String externalOrderId);

}

