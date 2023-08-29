package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Collection;
import com.teamchallenge.online_store.model.Product;
import com.teamchallenge.online_store.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addCollection(Collection collection) {
        collectionRepository.save(collection);
    }

    public List<Collection> getAllCollection() {
        return collectionRepository.findAll();
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Категорію не знайдено"));
    }

    public void updateCollection(Long id, Collection updatedCollection) {
        Collection existingCollection = getCollectionById(id);
        existingCollection.setName(updatedCollection.getName());
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
}
