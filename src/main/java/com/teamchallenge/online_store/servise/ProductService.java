package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.PageModel;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
        existingProduct.setCollection(updatedProduct.getCollection());
        existingProduct.setSeasonNovelties(updatedProduct.isSeasonNovelties());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public List<Product> getProductsByCollection(Collection collection) {
        return productRepository.findByCollection(collection);
    }

    public PageModel<Product> getSeasonNovelties(Pageable pageable) {
        Page<Product> page = productRepository.findBySeasonNoveltiesTrue(pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }

    public PageModel<Product> getPopularProducts(Pageable pageable) {
        Page<Product> page = productRepository.findByPopularProductsTrue(pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }

    public PageModel<Product> getSeasonNoveltiesAndPopularProducts (Pageable pageable) {
        Page<Product> page = productRepository.findBySeasonNoveltiesTrueAndPopularProductsTrue(pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }


    public PageModel<Product> getOtherProductsExceptSeasonNovelties(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllBy(pageable);

        List<Product> otherProducts = productPage.getContent()
                .stream()
                .filter(product -> !product.isSeasonNovelties())
                .collect(Collectors.toList());

        PageModel<Product> otherProductsPageModel = new PageModel<>();
        otherProductsPageModel.setContent(otherProducts);
        otherProductsPageModel.setPageNumber(productPage.getNumber());
        otherProductsPageModel.setPageSize(productPage.getSize());
        otherProductsPageModel.setTotalElement(productPage.getTotalElements());

        return otherProductsPageModel;
    }

    public PageModel<Product> getOtherProductsExceptPopularProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllBy(pageable);

        List<Product> otherProducts = productPage.getContent()
                .stream()
                .filter(product -> !product.isPopularProducts())
                .collect(Collectors.toList());

        PageModel<Product> otherProductsPageModel = new PageModel<>();
        otherProductsPageModel.setContent(otherProducts);
        otherProductsPageModel.setPageNumber(productPage.getNumber());
        otherProductsPageModel.setPageSize(productPage.getSize());
        otherProductsPageModel.setTotalElement(productPage.getTotalElements());

        return otherProductsPageModel;
    }


    public List<Product> getProductsByPopularity(Boolean popular) {
        if (popular) {
            return productRepository.findByPopularProducts(true);
        } else {
            return productRepository.findAll();
        }
    }

    public PageModel<Product> getProductsByFilters(Boolean seasonNovelties, Boolean popularProducts, Pageable pageable) {
        Specification<Product> where = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (seasonNovelties) {
                predicates.add(criteriaBuilder.isTrue(root.get("seasonNovelties")));
            }

            if (popularProducts) {
                predicates.add(criteriaBuilder.isTrue(root.get("popularProducts")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });

        Page<Product> products = productRepository.findAll(where, pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(products.getContent());
        pageModel.setPageNumber(products.getNumber());
        pageModel.setPageSize(products.getSize());
        pageModel.setTotalElement(products.getTotalElements());

        return pageModel;
    }

    public PageModel<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {

        Page<Product> page = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);

        PageModel<Product> pageModel = new PageModel<>();
        pageModel.setContent(page.getContent());
        pageModel.setPageNumber(page.getNumber());
        pageModel.setPageSize(page.getSize());
        pageModel.setTotalElement(page.getTotalElements());

        return pageModel;
    }
}
