package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Category;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @Operation(summary = "Add category")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return ResponseEntity.ok("Категорію успішно додано");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося додати категорію: " + e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get all categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }  /// перевірити роботу цього методу

    @GetMapping("/{categoryId}/products")
    @Operation(summary = "Get all products in category")
    public List<Product> getProductsByCategoryId (@PathVariable Long categoryId) {
        return categoryService.getProductsByCategoryId(categoryId);
    }
}
