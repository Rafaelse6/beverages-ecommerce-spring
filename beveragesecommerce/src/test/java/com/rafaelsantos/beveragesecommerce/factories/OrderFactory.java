package com.rafaelsantos.beveragesecommerce.factories;

import com.rafaelsantos.beveragesecommerce.entities.*;
import com.rafaelsantos.beveragesecommerce.entities.enums.OrderStatus;

import java.time.Instant;

public class OrderFactory {

    public static Order createOrder(User client){
        Order order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client, new Payment());

        Beverage beverage = BeverageFactory.createBeverage();
        OrderItem orderItem = new OrderItem(order,beverage, 2, 10.0);
        order.getItems().add(orderItem);

        return order;
    }
}
