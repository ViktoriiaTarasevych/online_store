package com.teamchallenge.online_store.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

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


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product(Long id, String name,Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Product() {
    }

    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

