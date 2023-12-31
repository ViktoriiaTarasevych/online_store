package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.CollectionService;
import com.teamchallenge.online_store.servise.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


//@CrossOrigin(origins = "http://localhost:3000, https://candle-shop-by-ninjas-team.vercel.app")
@RestController
@RequestMapping("/api")
@Tag(name = "Product")
public class ProductController {

    private final ProductService productService;

    private final CollectionService collectionService;

    public ProductController(ProductService productService, CollectionService collectionService) {
        this.productService = productService;
        this.collectionService = collectionService;
    }


    @PostMapping("/products/")
    @Operation(summary = "Add product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            Collection collection = collectionService.getCollectionById(product.getCollection().getId());
            product.setCollection(collection);
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
        product.getCollection().getCollectionName();
        return product;
    }


    @GetMapping("/products")
    @Operation(summary = "Get products")
    public ResponseEntity<PageModel<Product>> getProducts(
            @RequestParam(name = "season_novelties", required = false ) Boolean seasonNovelties,
            @RequestParam(name = "popular_products", required = false ) Boolean popularProducts,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int page_amount,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) BigDecimal min_price,
            @RequestParam(required = false) BigDecimal max_price) {

        Pageable pageable;
        if (offset != null && offset >= 0 && limit != null && limit > 0) {
            pageable = PageRequest.of(offset / limit, limit);
        } else {
            pageable = PageRequest.of(page, page_amount);
        }

        PageModel<Product> products;


        if (min_price != null && max_price != null) {
            products = productService.getProductsByPriceRange(min_price, max_price, pageable);
        } else if (seasonNovelties != null && popularProducts != null) {
            if (seasonNovelties && popularProducts) {
                products = productService.getSeasonNoveltiesAndPopularProducts(pageable);
            } else if (seasonNovelties) {
                products = productService.getSeasonNovelties(pageable);
            } else if (popularProducts) {
                products = productService.getPopularProducts(pageable);
            } else {
                products = productService.getAllProducts(pageable);
            }
        } else if (seasonNovelties != null) {
            if (seasonNovelties) {
                products = productService.getSeasonNovelties(pageable);
            } else {
                products = productService.getOtherProductsExceptSeasonNovelties(pageable);
            }
        } else if (popularProducts != null) {
            if (popularProducts) {
                products = productService.getPopularProducts(pageable);
            } else {
                products = productService.getOtherProductsExceptPopularProducts(pageable);
            }
        } else {
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

}