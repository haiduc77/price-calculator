package com.softwarehouse.pricecalculator.entities.rebate;

public class SpecialDealRebate implements Rebate{
    public static final String NAME = "SpecialDealRebate";
    public static final Double SPECIAL_DISCOUNT = 3.0;
    @Override
    public Double applyRebate(Double price) {
        return price*(100-SPECIAL_DISCOUNT)/100;
    }
    
}
