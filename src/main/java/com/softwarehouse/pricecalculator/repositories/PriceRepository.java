package com.softwarehouse.pricecalculator.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.softwarehouse.pricecalculator.entities.Discount;
import com.softwarehouse.pricecalculator.entities.Price;
import com.softwarehouse.pricecalculator.entities.Product;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long>{
   @Query("select d from Discount d")
   List<Discount> findAllDiscounts();
   @Query("select p from Product p")
   List<Product> findAllProducts();
   @Query("select d from Discount d where d.product.id = :productId")
   List<Discount> getDiscountsByProduct(Long productId);
}
