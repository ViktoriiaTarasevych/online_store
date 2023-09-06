package com.teamchallenge.online_store.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = READ_ONLY)
    private Long id;

    @Column(name = "name")
    private String productName;

    private String description;

    private BigDecimal price;
    @Column(name = "season_novelties", nullable = false)
    Boolean seasonNovelties = false;

    @Column(name = "popular_products")
    Boolean popularProducts;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }


    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public boolean isSeasonNovelties() {
        return seasonNovelties;
    }

    public void setSeasonNovelties(boolean seasonNovelties) {
        this.seasonNovelties = seasonNovelties;
    }


    public boolean isPopularProducts() {
        return popularProducts;
    }

    public void setPopularProducts(boolean popularProducts) {
        this.popularProducts = popularProducts;
    }

    public Product(Long id, String productName, String description, BigDecimal price, Collection collection) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.collection = collection;
    }

    public Product() {
    }

    public Product(Long id, String productName) {
        this.id = id;
        this.productName = productName;
    }
}

