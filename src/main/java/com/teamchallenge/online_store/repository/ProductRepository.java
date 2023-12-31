package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllBy(Pageable pageable);

    List<Product> findByCollection(Collection collection);

    Page<Product> findBySeasonNoveltiesTrue(Pageable pageable);

    Page<Product> findByPopularProductsTrue(Pageable pageable);

    Page<Product> findBySeasonNoveltiesTrueAndPopularProductsTrue(Pageable pageable);

    List<Product> findByPopularProducts(Boolean popular);

    Page<Product> findAll(Specification<Product> where, Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);
}




