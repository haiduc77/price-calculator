package com.softwarehouse.pricecalculator.entities.rebate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RebateMapper {
    private RebateMapper(){}
    public static final Optional<Rebate> mapRebate(String rebate){
        if(rebate.equalsIgnoreCase(BulkSellRebate.NAME)){
            return Optional.of(new BulkSellRebate());
        }
        if(rebate.equalsIgnoreCase(SpecialDealRebate.NAME)){
            return Optional.of(new SpecialDealRebate());
        }
        if(rebate.equalsIgnoreCase(TimeOfYearRebate.NAME)){
            return Optional.of(new TimeOfYearRebate());
        }
        return Optional.empty();
    }

    public static final List<String> rebateNames(){
        return Arrays.asList(BulkSellRebate.NAME, SpecialDealRebate.NAME, TimeOfYearRebate.NAME);
    }
}
