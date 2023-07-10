package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Category;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private final ProductService productService;

    private final CategoryRepository categoryRepository;

    public CategoryService(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }   /// імя null пише

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Категорію не знайдена"));
    }

    public void updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        categoryRepository.save(existingCategory);
    }


    public List<Product> getProductsByCategoryId(Long categoryId) {
        // Отримати категорію за ідентифікатором
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Категорію не знайдена"));

        // Отримати всі продукти за ідентифікатором категорії
        return productService.getProductsByCategoryId(categoryId);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
