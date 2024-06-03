package com.rafaelsantos.beveragesecommerce.services;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.DTO.OrderDTO;
import com.rafaelsantos.beveragesecommerce.entities.DTO.OrderItemDTO;
import com.rafaelsantos.beveragesecommerce.entities.Order;
import com.rafaelsantos.beveragesecommerce.entities.OrderItem;
import com.rafaelsantos.beveragesecommerce.entities.enums.OrderStatus;
import com.rafaelsantos.beveragesecommerce.repositories.BeverageRepository;
import com.rafaelsantos.beveragesecommerce.repositories.OrderItemRepository;
import com.rafaelsantos.beveragesecommerce.repositories.OrderRepository;
import com.rafaelsantos.beveragesecommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private BeverageRepository beverageRepository;
    private OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, BeverageRepository beverageRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.beverageRepository = beverageRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        for(OrderItemDTO itemDTO : dto.getItems()){
            Beverage beverage = beverageRepository.getReferenceById(itemDTO.getBeverageId());
            OrderItem item = new OrderItem(order, beverage, itemDTO.getQuantity(), beverage.getPrice());
            order.getItems().add(item);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
