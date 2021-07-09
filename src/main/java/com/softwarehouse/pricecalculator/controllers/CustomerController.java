package com.softwarehouse.pricecalculator.controllers;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.softwarehouse.pricecalculator.entities.Customer;
import com.softwarehouse.pricecalculator.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private static final String CUSTOMER_PATH = "customer";
    private static Logger LOGGER = Logger.getLogger(CustomerController.class.getName());
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return CUSTOMER_PATH + "/customers";
    }

    @GetMapping("/newcustomer")
    public String addNewCustomer(Customer customer) {
        return CUSTOMER_PATH + "/add-customer";
    }
    

    @GetMapping("/editcustomer/{id}")
    public String editCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return CUSTOMER_PATH + "/update-customer";
    }

    @GetMapping("/deletecustomer/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        customerRepository.delete(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return CUSTOMER_PATH + "/customers";
    }

    @PostMapping("/addcustomer")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return CUSTOMER_PATH + "/add-customer";
        }
        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return CUSTOMER_PATH + "/customers";
    }

    @PostMapping("/updatecustomer/{id}")
    public String updateCustomer(@PathVariable("id") long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setId(id);
            return CUSTOMER_PATH + "/update-customer";
        }
        
        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return CUSTOMER_PATH + "/customers";
    }
}
