package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllBy(Pageable pageable);

    List<Product> findAllByCategoryId(Long categoryId);

}
