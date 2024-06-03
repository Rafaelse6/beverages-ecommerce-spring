package com.rafaelsantos.beveragesecommerce.repositories;

import com.rafaelsantos.beveragesecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
