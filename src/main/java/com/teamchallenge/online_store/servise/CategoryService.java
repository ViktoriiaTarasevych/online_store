package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Category;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Категорію не знайдено"));
    }

    public void updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        //    existingCategory.setProducts(updatedCategory.getProducts()); перевірити потім це
        categoryRepository.save(existingCategory);
    }


    public Set<Product> getProductsByCategoryId(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return category.getProducts();
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
