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
@RequestMapping("/api")
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @PostMapping("/products/")
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

    @GetMapping("/products/{id}")
    @Operation(summary = "Get product by id")
    public Product getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        product.getCategory().getName();
        return product;
    }

    @GetMapping("/products")
    @Operation(summary = "Get products")
    public ResponseEntity<PageModel<Product>> getProducts(
            @RequestParam(name = "season_novelties") Boolean seasonNovelties,
            @RequestParam(name = "popular_products") Boolean popularProducts,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageModel<Product> products;

        if (seasonNovelties && popularProducts) {
            products = productService.getSeasonNoveltiesAndPopularProducts(pageable);
        } else if (seasonNovelties) {
            products = productService.getSeasonNovelties(pageable);
        } else if (popularProducts) {
            products = productService.getPopularProducts(pageable);
        } else if (!seasonNovelties) {
            products = productService.getOtherProductsExceptSeasonNovelties(pageable);
        } else if (!popularProducts) {
            products = productService.getOtherProductsExceptPopularProducts(pageable);
        } else if (!seasonNovelties && !popularProducts) {
            products = productService.getAllProducts(pageable);
        } else {
            products = productService.getAllProducts(pageable);
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/products1")
    @Operation(summary = "Get products")
    public ResponseEntity<PageModel<Product>> getProducts1(
            @RequestParam(name = "season_novelties") Boolean seasonNovelties,

            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageModel<Product> products;


         if (seasonNovelties) {
            products = productService.getSeasonNovelties(pageable);

        }  else if (!seasonNovelties) {
            products = productService.getOtherProductsExceptSeasonNovelties(pageable);
        }   else {
            products = productService.getAllProducts(pageable);
        }

        return ResponseEntity.ok(products);
    }




    @PutMapping("/products/{id}")
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


    @DeleteMapping("/products/{id}")
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


    @GetMapping("/testproducts")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "popular", required = false) Boolean popular) {
        List<Product> products;

        if (popular != null) {
            products = productService.getProductsByPopularity(popular);
        } else {
            products = productService.getProductsByPopularity(false); // Fetch all products by default
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/filter")
    public ResponseEntity<PageModel<Product>> getProductsByFilters(
            @RequestParam(name = "seasonNovelties") Boolean seasonNovelties,
            @RequestParam(name = "popularProducts") Boolean popularProducts,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageModel<Product> products = productService.getProductsByFilters(seasonNovelties, popularProducts, pageable);

        return ResponseEntity.ok(products);
    }

}