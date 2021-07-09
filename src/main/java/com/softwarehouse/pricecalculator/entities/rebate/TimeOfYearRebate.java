package com.softwarehouse.pricecalculator.entities.rebate;

public class TimeOfYearRebate implements Rebate{
    public static final String NAME = "TimeOfYearRebate";
    public static final Double TIME_OF_YEAR_DISCOUNT = 15.0;
    @Override
    public Double applyRebate(Double price) {
        return price*(100-TIME_OF_YEAR_DISCOUNT)/100;
    }
    
}
