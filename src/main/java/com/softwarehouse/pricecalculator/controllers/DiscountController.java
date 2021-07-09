package com.softwarehouse.pricecalculator.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwarehouse.pricecalculator.entities.Discount;
import com.softwarehouse.pricecalculator.entities.rebate.RebateMapper;
import com.softwarehouse.pricecalculator.repositories.DiscountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.logging.Logger;

@Controller
@RequestMapping("/discount")
public class DiscountController {
    private static final String DISCOUNT_PATH = "discount";
    private static Logger LOGGER = Logger.getLogger(DiscountController.class.getName());
    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("discounts", discountRepository.findAll());
        model.addAttribute("customers", discountRepository.findAllCustomers());
        model.addAttribute("products", discountRepository.findAllProducts());
        return DISCOUNT_PATH + "/discounts";
    }

    @GetMapping("/newdiscount")
    public String addNewDiscount(Discount discount, Model model) {
        model.addAttribute("products", discountRepository.findAllProducts());
        model.addAttribute("customers", discountRepository.findAllCustomers());
        model.addAttribute("names", RebateMapper.rebateNames());
        return DISCOUNT_PATH + "/add-discount";
    }
    

    @GetMapping("/editdiscount/{id}")
    public String editDiscount(@PathVariable("id") long id, Model model) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid discount Id:" + id));
        model.addAttribute("discount", discount);
        model.addAttribute("products", discountRepository.findAllProducts());
        model.addAttribute("customers", discountRepository.findAllCustomers());
        model.addAttribute("names", RebateMapper.rebateNames());
        return DISCOUNT_PATH + "/update-discount";
    }

    @GetMapping("/deletediscount/{id}")
    public String deleteDiscount(@PathVariable("id") long id, Model model) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid discount Id:" + id));
        discountRepository.delete(discount);
        model.addAttribute("discount", discountRepository.findAll());
        return DISCOUNT_PATH + "/discounts";
    }

    @PostMapping("/adddiscount")
    public String addDiscount(@Valid Discount discount, BindingResult result, Model model) {
        model.addAttribute("products", discountRepository.findAllProducts());
        model.addAttribute("customers", discountRepository.findAllCustomers());
        model.addAttribute("names", RebateMapper.rebateNames());
        if (result.hasErrors()) {
            return DISCOUNT_PATH + "/add-discount";
        }
        discountRepository.save(discount);
        model.addAttribute("discounts", discountRepository.findAll());
        return DISCOUNT_PATH + "/discounts";
    }

    @PostMapping("/updatediscount/{id}")
    public String updateDiscount(@PathVariable("id") long id,@Valid Discount discount, BindingResult result, Model model) {
        model.addAttribute("products", discountRepository.findAllProducts());
        model.addAttribute("customers", discountRepository.findAllCustomers());
        model.addAttribute("names", RebateMapper.rebateNames());
        if (result.hasErrors()) {
            discount.setId(id);
            return DISCOUNT_PATH + "/update-discount";
        }
        discountRepository.save(discount);
        model.addAttribute("discounts", discountRepository.findAll());
       
        return DISCOUNT_PATH + "/discounts";
    }

    @GetMapping("/importdiscounts")
    public String importCustomers(Model model) {
        discountRepository.deleteAll();
        importFromJson();
        model.addAttribute("discounts", discountRepository.findAll());
        return DISCOUNT_PATH + "/discounts";
    }

    private void importFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Discount>> typeReference = new TypeReference<List<Discount>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/discounts.json");
        try {
            List<Discount> discounts = mapper.readValue(inputStream,typeReference);
            discounts.forEach(discount ->discountRepository.save(discount));
            LOGGER.info("Discounts imported successfully.");
        } catch (IOException e){
            LOGGER.warning("Unable to import discounts: " + e.getMessage());
        }
    }
}
