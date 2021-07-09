package com.softwarehouse.pricecalculator.repositories;

import com.softwarehouse.pricecalculator.entities.Customer;
import com.softwarehouse.pricecalculator.entities.Discount;
import com.softwarehouse.pricecalculator.entities.Product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
   @Query("select c from Customer c")
   List<Customer> findAllCustomers();
   @Query("select p from Product p")
   List<Product> findAllProducts();
}
