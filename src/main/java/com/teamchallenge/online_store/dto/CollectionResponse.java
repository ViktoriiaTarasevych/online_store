package com.teamchallenge.online_store.dto;

import com.teamchallenge.online_store.model.Collection;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class CollectionResponse {

    private Collection collection;
    private byte[] imageBytes;

    public CollectionResponse(Collection collection, Resource imageResource) {
        this.collection = collection;
        try {
            this.imageBytes = IOUtils.toByteArray(imageResource.getInputStream());
        } catch (IOException e) {
            // Обробляйте помилки, які можуть виникнути при читанні ресурсу
            e.printStackTrace();
            this.imageBytes = new byte[0]; // Або встановлюйте значення за замовчуванням.
        }
    }

    public Collection getCollection() {
        return collection;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
