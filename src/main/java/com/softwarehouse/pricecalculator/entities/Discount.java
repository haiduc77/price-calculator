package com.softwarehouse.pricecalculator.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    Customer customer;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    Product product;

    @NotBlank(message = "Discount name is required")
    private String name;
    
    public Discount(){}

    public Discount(long id, Customer customer,
            Product product, @NotBlank(message = "Discount name is required") String name) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
  
}
