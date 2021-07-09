package com.softwarehouse.pricecalculator.controllers.pricecalculator;

public abstract class PriceCalculator {
    public abstract Double calculatePrice(Double price, Integer discountPercentage, Double rawDiscount);
    
}
