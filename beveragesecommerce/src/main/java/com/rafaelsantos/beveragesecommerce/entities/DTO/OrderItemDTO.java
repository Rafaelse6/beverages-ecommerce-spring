package com.rafaelsantos.beveragesecommerce.entities.DTO;

import com.rafaelsantos.beveragesecommerce.entities.OrderItem;

public class OrderItemDTO {

    private Long beverageId;
    private String name;
    private Double price;
    private Integer quantity;
    private String imgUrl;

    public OrderItemDTO(Long beverageId, String name, Double price, Integer quantity, String imgUrl) {
        this.beverageId = beverageId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
    }

    public OrderItemDTO(OrderItem entity){
        beverageId = entity.getBeverage().getId();
        name = entity.getBeverage().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
        imgUrl = entity.getBeverage().getImgUrl();
    }
    public Long getBeverageId() {
        return beverageId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubTotal() {
        return price * quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
