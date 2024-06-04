package com.rafaelsantos.beveragesecommerce.factories;

import com.rafaelsantos.beveragesecommerce.entities.Category;

public class CategoryFactory {

    public static Category createCategory(){
        return new Category(1L, "Imported");
    }

    public static Category createCategory(Long id, String name){
        return new Category(id, name);
    }
}
