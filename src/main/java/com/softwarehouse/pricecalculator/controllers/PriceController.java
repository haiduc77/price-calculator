package com.softwarehouse.pricecalculator.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.softwarehouse.pricecalculator.entities.Discount;
import com.softwarehouse.pricecalculator.entities.Price;
import com.softwarehouse.pricecalculator.entities.Product;
import com.softwarehouse.pricecalculator.entities.rebate.Rebate;
import com.softwarehouse.pricecalculator.entities.rebate.RebateMapper;
import com.softwarehouse.pricecalculator.repositories.PriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/price")
public class PriceController {
    private static final String PRICE_PATH = "price";
    private final PriceRepository priceRepository;

    @Autowired
    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        priceRepository.deleteAll();
        
        List<Product> products = priceRepository.findAllProducts();
        List<Price> prices = new ArrayList<>();
        products.forEach(product->{
                Price price = new Price();
                List<Discount> discounts = priceRepository.getDiscountsByProduct(product.getId());
                discounts.forEach(discount->{
                    price.setDiscount(discount);
                    Optional<Rebate> rebate = RebateMapper.mapRebate(discount.getName());
                    if(rebate.isPresent()){
                        price.setDiscountedValue(rebate.get().applyRebate(product.getPrice()));
                    }else{
                        price.setDiscountedValue(product.getPrice());
                    }
                });
                
                prices.add(price);
            });
        model.addAttribute("prices", prices);
        return PRICE_PATH + "/prices";
    }
}


