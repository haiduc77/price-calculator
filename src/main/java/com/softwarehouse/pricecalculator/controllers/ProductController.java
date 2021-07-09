package com.softwarehouse.pricecalculator.controllers;

import java.util.logging.Logger;

import javax.validation.Valid;

import com.softwarehouse.pricecalculator.entities.Product;
import com.softwarehouse.pricecalculator.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final String PRODUCT_PATH = "product";
    private static Logger LOGGER = Logger.getLogger(ProductController.class.getName());
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return PRODUCT_PATH + "/products";
    }

    @GetMapping("/newproduct")
    public String addNewProduct(Product product) {
        return PRODUCT_PATH + "/add-product";
    }
    

    @GetMapping("/editproduct/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return PRODUCT_PATH + "/update-product";
    }

    @GetMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        productRepository.delete(product);
        model.addAttribute("products", productRepository.findAll());
        return PRODUCT_PATH + "/products";
    }

    @PostMapping("/addproduct")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return PRODUCT_PATH + "/add-product";
        }
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return PRODUCT_PATH + "/products";
    }

    @PostMapping("/updateproduct/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return PRODUCT_PATH + "/update-product";
        }
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return PRODUCT_PATH + "/products";
    }
}
