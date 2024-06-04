package com.rafaelsantos.beveragesecommerce.factories;

import com.rafaelsantos.beveragesecommerce.entities.Beverage;
import com.rafaelsantos.beveragesecommerce.entities.Category;

public class BeverageFactory {

    public static Beverage createBeverage(){
        Category category = CategoryFactory.createCategory();
        Beverage beverage = new Beverage(1L, "Heineken","Heineken description", 15.0, "Heineken URL");

        beverage.getCategories().add(category);
        return beverage;
    }

    public static Beverage createBeverate(String name){
        Beverage beverage = createBeverage();
        beverage.setName(name);
        return beverage;
    }
}
