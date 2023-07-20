package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Category;
import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
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
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Продукт не знайдено"));
    }

    public void updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public List<Product> getProductsByCategory (Category category) {
        return productRepository.findByCategory(category);
    }
}
