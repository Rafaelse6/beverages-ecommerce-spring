package com.rafaelsantos.beveragesecommerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "beverage_id")
    private Beverage beverage;

    public OrderItemPK() {
    }

    public Order getOrder() {
        return order;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, beverage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(order, that.order) && Objects.equals(beverage, that.beverage);
    }
}
