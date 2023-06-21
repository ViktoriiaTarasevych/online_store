package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            // Виклик сервісного шару для додавання товару
            productService.addProduct(product);
            return ResponseEntity.ok("Product added successfully");
        } catch (Exception e) {
            // Обробка помилок, якщо виникла проблема з додаванням товару
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product: " + e.getMessage());
        }

    }
    @GetMapping("/api/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        // Логіка для отримання товару з бази даних або іншого джерела
        Product product = productService.getProductById(id);
        return product;
    }
}