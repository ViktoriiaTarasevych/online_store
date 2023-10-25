package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/api/collection")
@Tag(name = "Collection")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/")
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        Collection savedCollection = collectionService.saveCollection(collection);
        return ResponseEntity.ok(savedCollection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get collection by id")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        try {
            Collection collection = collectionService.getCollectionById(id);
            return ResponseEntity.ok(collection);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update collection by id")
    public ResponseEntity<String> updateCategoryById(@PathVariable Long id, @RequestBody Collection updatedCollection) {
        try {
            collectionService.updateCollection(id, updatedCollection);
            return ResponseEntity.ok("Колекцію оновлено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося оновити колекцію : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete collection by id")
    public ResponseEntity<String> deleteCollectionById(@PathVariable Long id) {
        try {
            collectionService.deleteCollection(id);
            return ResponseEntity.ok("Колекцію видалено");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Колекцію не знайдено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося видалити колекцію: " + e.getMessage());
        }
    }

    @GetMapping("/")
    @Operation(summary = "Get all collections")
    public List<Collection> getAllCategories() {
        return collectionService.getAllCollection();
    }

    @GetMapping("/{collectionId}/products")
    @Operation(summary = "Get all products in collection")
    public Set<Product> getProductsByCategoryId(@PathVariable Long collectionId) {
        return collectionService.getProductsByCollectionId(collectionId);
    }


    @PostMapping("/{collectionId}/images")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long collectionId,
                                                     @RequestParam("file") MultipartFile file) {
        try {
            collectionService.uploadCollectionImage(collectionId, file);
            return ResponseEntity.ok("Profile image uploaded successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload profile image: " + e.getMessage());
        }
    }
}
