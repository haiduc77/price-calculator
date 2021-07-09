package com.softwarehouse.pricecalculator.repositories;

import com.softwarehouse.pricecalculator.entities.Customer;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    List<Customer> findByLastname(String lastname);
    
}
