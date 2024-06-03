package com.rafaelsantos.beveragesecommerce.repositories;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    @Query("SELECT obj FROM Beverage obj " +
            "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Beverage> searchByName(String name, Pageable pageable);
}
