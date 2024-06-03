package com.rafaelsantos.beveragesecommerce.repositories;

import com.rafaelsantos.beveragesecommerce.entities.OrderItem;
import com.rafaelsantos.beveragesecommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
