package com.teamchallenge.online_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
public class Collection {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @NotNull
    private String name;

    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
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


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Collection() {
    }

    public Collection(String name) {
        this.name = name;
    }

    @Schema(accessMode = READ_ONLY)
    public List<Long> getProductIds() {
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        return productIds;
    }
}
