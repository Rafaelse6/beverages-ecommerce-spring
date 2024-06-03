package com.rafaelsantos.beveragesecommerce.repositories;

import com.rafaelsantos.beveragesecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
