package com.softwarehouse.pricecalculator.entities.rebate;

public class BulkSellRebate implements Rebate{
    public static final String NAME = "BulkSellRebate";
    public static final Double BULK_DISCOUNT = 5.5;
    @Override
    public Double applyRebate(Double price) {
        return price*(100-BULK_DISCOUNT)/100;
    }
    
}
