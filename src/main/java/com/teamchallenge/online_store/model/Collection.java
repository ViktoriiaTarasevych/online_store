package com.teamchallenge.online_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Entity
@Data
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    @NotNull
    @Column(name = "name")
    private String collectionName;

    @JsonIgnore
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private Set<Product> products;


    @Column(columnDefinition = "bytea", name = "collectionImage")
    private byte[] collectionImage;

    public Collection() {
    }

    public Collection(Long id, String collectionName, Set<Product> products, byte[] collectionImage) {
        this.id = id;
        this.collectionName = collectionName;
        this.products = products;

        this.collectionImage = collectionImage;
    }

    public Collection(String collectionName) {
        this.collectionName = collectionName;
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
