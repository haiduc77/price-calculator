package com.softwarehouse.pricecalculator.repositories;

import com.softwarehouse.pricecalculator.entities.Product;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends CrudRepository<Product, Long> {   
    List<Product> findByName(String name);

}
