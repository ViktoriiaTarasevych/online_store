package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        // Логіка для додавання товару
        productRepository.save(product);
    }

    public PageModel<Product> getAllProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }

    public Product getProductById(Long id) {
        // Пошук товару за ідентифікатором
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public void updateProduct(Long id, Product updatedProduct) {
        // Пошук товару за ідентифікатором
        Product existingProduct = getProductById(id);

        // Оновлення властивостей товару
        existingProduct.setName(updatedProduct.getName());
        // Додаткові оновлення властивостей товару, якщо необхідно

        // Збереження оновленого товару
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        // Пошук товару за ідентифікатором
        Product product = getProductById(id);

        // Видалення товару
        productRepository.delete(product);
    }
}
