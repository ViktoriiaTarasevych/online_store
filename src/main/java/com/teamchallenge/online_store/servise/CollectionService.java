package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }
    public Collection saveCollection(Collection collection) {
        return collectionRepository.save(collection);
    }


    public List<Collection> getAllCollection() {
        return collectionRepository.findAll();
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Колекцію не знайдено"));
    }

    public void updateCollection(Long id, Collection updatedCollection) {
        Collection existingCollection = getCollectionById(id);
        existingCollection.setCollectionName(updatedCollection.getCollectionName());
        collectionRepository.save(existingCollection);
    }


    public Set<Product> getProductsByCollectionId(Long collectionId) {
        Collection collection = getCollectionById(collectionId);
        return collection.getProducts();
    }

    public void deleteCollection(Long id) {
        Collection collection = getCollectionById(id);
        collectionRepository.delete(collection);
    }



    public void uploadCollectionImage(Long collectionId, MultipartFile file) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes = file.getBytes();
                collection.setCollectionImage(imageBytes);
                collectionRepository.save(collection);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile image: " + e.getMessage());
            }
        }
    }
}

