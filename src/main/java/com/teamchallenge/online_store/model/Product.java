package com.teamchallenge.online_store.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Data
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images;

    private Long previewImageId;


    public boolean isSeasonNovelties() {
        return seasonNovelties;
    }

    public boolean isPopularProducts() {
        return popularProducts;
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

