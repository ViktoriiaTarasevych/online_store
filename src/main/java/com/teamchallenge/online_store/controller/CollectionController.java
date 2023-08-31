package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.dto.CollectionResponse;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.servise.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Add collection")
    public ResponseEntity<String> addCategory(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute Collection collection) {
        try {
            byte[] imageBytes = file.getBytes();
            collection.setImage(imageBytes);
            collectionService.addCollection(collection);
            return ResponseEntity.ok("Колекцію успішно додано");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Не вдалося додати колекцію: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get collection by id")
    public ResponseEntity<CollectionResponse> getCollectionById(@PathVariable Long id) {
        Collection collection = collectionService.getCollectionById(id);

        if (collection == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = collection.getImage();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // або інший MediaType в залежності від типу зображення

        return ResponseEntity.ok()
                .headers(headers)
                .body(new CollectionResponse(collection, imageBytes));
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
}
