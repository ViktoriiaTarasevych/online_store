package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000, https://candle-shop-by-ninjas-team.vercel.app")
@RestController
@RequestMapping("/api/products")
@Api(tags = "Product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping("/")
//    public String hello (String hello) {
//
//        return "Привіт";
//    }


    @PostMapping("/")
    @ApiOperation("Add product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            // Виклик сервісного шару для додавання товару
            productService.addProduct(product);
            return ResponseEntity.ok("Продукт успішно додано");
        } catch (Exception e) {
            // Обробка помилок, якщо виникла проблема з додаванням товару
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося додати продукт: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public Product getProductById(@PathVariable Long id) {
        // Логіка для отримання товару з бази даних
        Product product = productService.getProductById(id);
        return product;
    }

    @GetMapping("/")
    @ApiOperation("Get all products")
    public PageModel<Product> getAllProducts ( @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size ) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }
}