package com.teamchallenge.online_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products != null ? products : new HashSet<>();
    }

    public Category() {
    }


    public Category(Long id, String name, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products != null ? products : new HashSet<>();
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Long> getProductIds() {
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        return productIds;
    }
}
