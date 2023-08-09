package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Category;
import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.CategoryService;
import com.teamchallenge.online_store.servise.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//@CrossOrigin(origins = "http://localhost:3000, https://candle-shop-by-ninjas-team.vercel.app")
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @PostMapping("/")
    @Operation(summary = "Add product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);
            productService.addProduct(product);
            return ResponseEntity.ok("Продукт успішно додано");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося додати продукт: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
         product.getCategory().getName();
        return product;
    } /// не виводяться імя продукту та категорії id та імя

    @GetMapping("/")
    @Operation(summary = "Get all products")
    public PageModel<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public ResponseEntity<String> updateProductById(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok("Продукт оновлено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося оновити продукт: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Продукт успішно видалено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Продукт не знайдено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося видалити продукт: " + e.getMessage());
        }
    }

    @GetMapping("/season-novelties")
    @Operation(summary = "Get season novelties")
    public ResponseEntity<PageModel<Product>> getProducts(
            @RequestParam(name = "season_novelties", defaultValue = "false") boolean seasonNovelties,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (seasonNovelties) {
            Pageable pageable = PageRequest.of(page, size);
            PageModel<Product> seasonNoveltiesPage = productService.getSeasonNovelties(pageable);
            return ResponseEntity.ok(seasonNoveltiesPage);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            PageModel<Product> allProductsPage = productService.getAllProducts(pageable);
            return ResponseEntity.ok(allProductsPage);
        }
    }

}